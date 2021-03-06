function Player(id, name, color) {
	this.id = id;
	this.name = name;
	this.color = color;

	this.victoryPoints = 0;
	this.resourceCards = 0;
	this.developmentCards = 0;
	this.playedKnights = 0;
	this.roads = 0;
	this.settlements = 0;
	this.cities = 0;
	this.largestArmy = false;
	this.longestRoad = false;
}

Player.prototype.fillPlayerTab = function() {
	var tab = $("#p" + (this.id + 1) + "-tab");
	tab.empty();
	
	tab.append("<p><strong>Name:</strong> " + this.name + "</p>");
	tab.append("<div class='circle player-circle' style='background-color: " + this.color + "'></div>");
	tab.append("<p><strong>Victory Points:</strong> " + this.victoryPoints + "</p>");
	tab.append("<p><strong>Resource Cards:</strong> " + this.resourceCards + "</p>");
	tab.append("<p><strong>Development Cards:</strong> " + this.developmentCards + "</p>");
	tab.append("<p><strong>Played Knights:</strong> " + this.playedKnights + "</p>");
	tab.append("<p><strong>Roads:</strong> " + this.roads + "</p>");
	tab.append("<p><strong>Settlements:</strong> " + this.settlements + "</p>");
	tab.append("<p><strong>Cities:</strong> " + this.cities + "</p>");
}

function parsePlayers(playersData) {
	var players = [];

	for (var i = 0; i < playersData.length; i++) {
		var playerData = playersData[i];

		// Construct player from playerData object
		var player = new Player(playerData.id, playerData.name, playerData.color);
		player.victoryPoints = playerData.victoryPoints;
		player.playedKnights = playerData.numPlayedKnights;
		player.roads = playerData.numRoads;
		player.settlements = playerData.numSettlements;
		player.cities = playerData.numCities;
		player.largestArmy = playerData.largestArmy;
		player.longestRoad = playerData.longestRoad;
		player.resourceCards = playerData.numResourceCards;
		player.developmentCards = playerData.numDevelopmentCards;

		players.push(player);
	}

	return players;
}

function fillPlayerHand(handData) {
	$("#brick-number").text(handData.resources.BRICK);
	$("#wood-number").text(handData.resources.WOOD);
	$("#ore-number").text(handData.resources.ORE);
	$("#wheat-number").text(handData.resources.WHEAT);
	$("#sheep-number").text(handData.resources.SHEEP);

	$("#knight-number").text(handData.devCards["Knight"]);
	$("#year-of-plenty-number").text(handData.devCards["Year of Plenty"]);
	$("#monopoly-number").text(handData.devCards["Monopoly"]);
	$("#road-building-number").text(handData.devCards["Road Building"]);
	$("#victory-point-number").text(handData.devCards["Victory Point"]);
}

function createTradeRateText(number) {
	return number + ":1";
}

function fillPlayerTradeRates(rates) {
	$("#brick-trade-rate").text(createTradeRateText(rates.BRICK));
	$("#wood-trade-rate").text(createTradeRateText(rates.WOOD));
	$("#ore-trade-rate").text(createTradeRateText(rates.ORE));
	$("#wheat-trade-rate").text(createTradeRateText(rates.WHEAT));
	$("#sheep-trade-rate").text(createTradeRateText(rates.SHEEP));
	$("#wildcard-trade-rate").text(createTradeRateText(rates.WILDCARD));
}

