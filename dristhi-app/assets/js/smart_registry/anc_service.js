angular.module("smartRegistry.services")
    .service('ANCService', function (SmartHelper) {
        var schedules =
            [
                {
                    name: "anc",
                    milestones: ["ANC 1", "ANC 2", "ANC 3", "ANC 4"],
                    services: ["ANC 1", "ANC 2", "ANC 3", "ANC 4"],
                    is_list: false
                },
                {
                    name: "tt",
                    milestones: ["TT 1", "TT 2", "TT Booster"],
                    services: ["TT 1", "TT 2", "TT Booster"],
                    is_list: false
                },
                {
                    name: "ifa",
                    milestones: ["IFA 1", "IFA 2", "IFA 3"],
                    services: ['IFA'],
                    is_list: true
                },
                {
                    name: "hb",
                    milestones: ["Hb Test 1", "Hb Test 2"],
                    services: ["Hb Test"],
                    is_list: true
                },
                {
                    name: 'delivery_plan',
                    milestones: ['Delivery Plan'],
                    services: ['Delivery Plan'],
                    is_list: false
                },
                {
                    name: 'pnc',
                    milestones: ["PNC"],
                    services: ["PNC"],
                    is_list: true
                }
            ];

        var alert_status = {
            NORMAL: "normal",
            URGENT: "urgent",
            COMPLETE: "complete",
            UPCOMING: "upcoming"
        };

        var preProcessSchedule = function(client, schedule){
            var visit = {};
            var alertsForCurrentSchedule = client.alerts.filter(function (alert) {
                return schedule.milestones.indexOf(alert.name) > -1;
            });

            for (var i = schedule.milestones.length - 1; i > -1; i--) {
                var milestone = schedule.milestones[i];
                var milestone_alert = alertsForCurrentSchedule.find(function (schedule_alert) {
                    return schedule_alert.name === milestone;
                });
                if (milestone_alert !== undefined ||
                        (milestone_alert !== undefined && visit.next !== undefined &&
                            milestone_alert.status !== alert_status.COMPLETE)) {
                    var next_milestone = {};
                    next_milestone.name = milestone_alert.name;
                    next_milestone.status = milestone_alert.status;
                    next_milestone.visit_date = milestone_alert.date;
                    visit.next = next_milestone;
                    visit[next_milestone.name] = {
                        status: next_milestone.status,
                        visit_date: next_milestone.visit_date
                    };
                    // only break if status is not complete so we can keep looking for other in-complete milestones
                    if(milestone_alert.status !== alert_status.COMPLETE)
                        break;
                }
            }

            var servicesForCurrentSchedule = client.services_provided.filter(function (service_provided) {
                return schedule.services.indexOf(service_provided.name) !== -1;
            });
            for (var i = schedule.services.length - 1; i > -1; i--) {
                var service_name = schedule.services[i];
                var services_provided = servicesForCurrentSchedule.filter(function (service) {
                    return service.name === service_name;
                });

                if (services_provided.length > 0) {
                    if(schedule.is_list)
                    {
                        var services = [];
                        services_provided.forEach(function(service_provided){
                            var service = {};
                            service.status = alert_status.COMPLETE;
                            service.visit_date = service_provided.date;
                            service.data = service_provided.data;
                            services.push(service);
                        });
                        visit[service_name] = services;
                        if (visit.previous === undefined) {
                            var previous = services_provided.sort(function(a, b){
                                if(a.date < b.date)
                                    return 1;
                                else if(a.date > b.date)
                                    return -1;
                                else
                                    return 0;
                            })[0];
                            visit.previous = {
                                name: previous.name,
                                status: alert_status.COMPLETE,
                                visit_date: previous.date,
                                data: previous.data
                            };
                        }
                    }
                    else
                    {
                        var service = {};
                        service.status = alert_status.COMPLETE;
                        service.visit_date = services_provided[0].date;
                        service.data = services_provided[0].data;
                        visit[services_provided[0].name] = service;

                        if (visit.previous === undefined &&
                            (visit.next === undefined ||
                                (visit.next !== undefined && visit.next.name !== services_provided[0].name))) {
                            visit.previous = services_provided[0].name;
                            visit.previous = {
                                name: services_provided[0].name,
                                status: alert_status.COMPLETE,
                                visit_date: services_provided[0].date,
                                data: services_provided[0].data
                            };
                        }
                    }
                }
            }
            client.visits[schedule.name] = visit;
        };

        return {
            schedules: schedules,
            status: alert_status,
            preProcessSchedule: preProcessSchedule,
            preProcess: function (clients) {
                clients.forEach(function (client) {
                        client.visits = {};
                        schedules.forEach(function (schedule) {
                            preProcessSchedule(client, schedule)
                        });
                        // calculate days between today and EDD
                        var days_past_edd;
                        var edd_date = Date.parse(client.edd);
                        if (edd_date) {
                            client.days_past_edd = Math.ceil(SmartHelper.daysBetween(new Date(edd_date), new Date()));
                        }
                    }
                );
                return clients;
            }
        }
    });
