angular.module("smartRegistry.controllers")
    .controller("pncRegisterController", function ($scope, SmartHelper, ANCService) {
        $scope.bridge = new PNCRegistryBridge();
        $scope.getClients = function () {
            return ANCService.preProcess($scope.bridge.getClients());
        };

        $scope.clients = $scope.getClients();

        $scope.sortOptions = {
            type: "sort",
            options: [
                {
                    label: "Name (A to Z)",
                    handler: "sortByName",
                    sortDescending: false
                },
                {
                    label: "Date of Delivery",
                    handler: "sortByDeliveryDate",
                    sortDescending: false
                },
                {
                    label: "HR",
                    handler: "sortByRisk",
                    sortDescending: false
                },
                {
                    label: "BPL",
                    handler: "sortByBPL"
                },
                {
                    label: "SC",
                    handler: "sortBySC"
                },
                {
                    label: "ST",
                    handler: "sortByST"
                }
            ]
        };

        $scope.defaultSortOption = $scope.sortOptions.options[0];
        $scope.currentSortOption = $scope.defaultSortOption;
        $scope.sortList = $scope.sortByName;
        $scope.sortDescending = true;

        $scope.sortByDeliveryDate = function (item) {
            return item.deliveryDate;
        };

        $scope.sortByRisk = function (item) {
            return !item.isHighRisk;
        };

        $scope.sortByBPL = function (client) {
            return client.economicStatus !== "bpl";
        };
        $scope.sortBySC = function (client) {
            return client.caste !== "sc";
        };
        $scope.sortByST = function (client) {
            return client.caste !== "st";
        };

        $scope.defaultVillageOptions = {
            type: "filterVillage",
            options: [
                {
                    label: "All",
                    id: "",
                    handler: "filterByInAreaLocationStatus"
                },
                {
                    label: "O/A",
                    id: "out_of_area",
                    handler: "filterByLocationStatus"
                },
                {
                    label: "L/P",
                    id: "left_the_place",
                    handler: "filterByLocationStatus"
                }
            ]
        };

        $scope.locationStatusMapping = {
            "out_of_area": 1,
            "left_the_place": 2
        };

        $scope.defaultVillageFilterHandler = "filterByVillageName";

        $scope.defaultVillage = $scope.defaultVillageOptions.options[0];
        $scope.villageFilterOption = $scope.defaultVillage;
        $scope.filterByInAreaLocationStatus = function (client, option) {
            return client.locationStatus !== "left_the_place";
        };
        $scope.filterByVillageName = function (client, option) {
            return client.village.toUpperCase() === option.id.toUpperCase();
        };
        $scope.filterByLocationStatus = function (client, option) {
            return client.locationStatus === option.id;
        };

        $scope.pncServiceOptions = {
            type: "pncService",
            options: [
                {
                    label: "Overview",
                    id: "overview",
                    handler: "changeContentBasedOnServiceMode"
                },
                {
                    label: "Benefits",
                    id: "benefits",
                    handler: "changeContentBasedOnServiceMode"
                },
                {
                    label: "PNC Visits",
                    id: "pnc_visits",
                    handler: "changeContentBasedOnServiceMode"
                }
            ]
        };

        $scope.defaultPNCServiceOption = $scope.pncServiceOptions.options[2];
        $scope.serviceModeOption = $scope.defaultPNCServiceOption;

        $scope.pncService = function (option) {
            $scope.serviceModeOption = option;
        };

        $scope.searchFilterString = "";

        $scope.contentTemplate = $scope.pncServiceOptions.options[0].id;

        $scope.searchCriteria = function (client, searchFilterString) {
            return ((client.name && client.name.toUpperCase().indexOf(searchFilterString.toUpperCase()) === 0)
                || (client.ec_number && client.ec_number.toUpperCase().indexOf(searchFilterString.toUpperCase()) === 0)
                || (client.thayi && client.thayi.toUpperCase().indexOf(searchFilterString.toUpperCase()) === 0));
        };

        $scope.changeContentBasedOnServiceMode = function (client, serviceModeOptionId) {
            $scope.contentTemplate = serviceModeOptionId;
            return true;
        };

        $scope.currentOptions = null;

        $scope.isModalOpen = false;

        $scope.isPNCFormModalOpen = false;

        $scope.openPNCFormModal = function (clientEntityId) {
            $scope.currentClientEntityId = clientEntityId;
            $scope.isPNCFormModalOpen = true;
        };

        $scope.closePNCFormModal = function () {
            $scope.currentClientEntityId = null;
            $scope.isPNCFormModalOpen = false;
        };

        $scope.daysPP = function(client) {
            return Math.round(SmartHelper.daysBetween(new Date(Date.parse(client.deliveryDate)), new Date()));
        };

        $scope.isPNCService = function(service) {
            return service['name'] === "PNC";
        };
    });
