package edu.brown.cs.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import edu.brown.cs.actions.Action;
import edu.brown.cs.actions.BuildCity;
import edu.brown.cs.actions.BuildRoad;
import edu.brown.cs.actions.BuildSettlement;
import edu.brown.cs.actions.BuyDevelopmentCard;
import edu.brown.cs.actions.EmptyAction;
import edu.brown.cs.actions.EndTurn;
import edu.brown.cs.actions.FollowUpAction;
import edu.brown.cs.actions.PlayKnight;
import edu.brown.cs.actions.PlayMonopoly;
import edu.brown.cs.actions.PlayRoadBuilding;
import edu.brown.cs.actions.PlayYearOfPlenty;
import edu.brown.cs.actions.ProposeTrade;
import edu.brown.cs.actions.StartGame;
import edu.brown.cs.actions.TradeWithBank;
import edu.brown.cs.actions.UpdateResource;
import edu.brown.cs.board.HexCoordinate;
import edu.brown.cs.board.IntersectionCoordinate;
import edu.brown.cs.catan.MasterReferee;
import edu.brown.cs.catan.Player;
import edu.brown.cs.catan.Referee;

public class ActionFactory {

  private Referee _referee;
  private final Gson GSON = new Gson();

  public ActionFactory(Referee referee) {
    assert referee != null;
    assert referee instanceof MasterReferee;
    _referee = referee;
  }

  private JsonObject convertFromStringToJson(String string) {
    return GSON.fromJson(string, JsonObject.class);
  }

  public Action createAction(String json) throws WaitingOnActionException {
    try {
      JsonObject actionJSON = convertFromStringToJson(json);
      return createAction(actionJSON);
    } catch (JsonSyntaxException e) {
      throw new IllegalArgumentException("The JSON contains an error: "
          + e.getLocalizedMessage());
    }
  }

  public Action createAction(JsonObject actionJSON)
    throws WaitingOnActionException {
    int playerID;
    String action;
    try {
      playerID = actionJSON.get("player").getAsInt();
      action = actionJSON.get("action").getAsString();
    } catch (JsonSyntaxException | NullPointerException e) {
      throw new IllegalArgumentException(
          "Missing PlayerID which is required for all actions.");
    }
    if (_referee.getTurn().waitingForFollowUp()) {
      FollowUpAction nextAction = _referee.getNextFollowUp(playerID);
      if (nextAction != null && action.equals(nextAction.getID())) {
        // Set up the action:
        nextAction.setupAction(_referee, playerID, actionJSON);
        return nextAction;
      }
      if (nextAction == null) {
        for (Player p : _referee.getPlayers()) {
          nextAction = _referee.getNextFollowUp(p.getID());
          if (nextAction != null) {
            throw new WaitingOnActionException(nextAction.getVerb(), p.getID(),
                _referee.getReadOnlyReferee());
          }
        }
      }
      return new EmptyAction(); //Should never be reached
    } else {
      try {
        switch (action) {
        case "getInitialState":
          return new EmptyAction();
        case StartGame.ID:
          return new StartGame(_referee);
        case BuildCity.ID:
          return new BuildCity(_referee, playerID,
              toIntersectionCoordinate(actionJSON.get("coordinate")
                  .getAsJsonObject()));
        case BuildSettlement.ID:
          return new BuildSettlement(_referee, playerID,
              toIntersectionCoordinate(actionJSON.get("coordinate")
                  .getAsJsonObject()), true);
        case BuildRoad.ID:
          IntersectionCoordinate start = toIntersectionCoordinate(actionJSON
              .get("start").getAsJsonObject());
          IntersectionCoordinate end = toIntersectionCoordinate(actionJSON.get(
              "end").getAsJsonObject());
          return new BuildRoad(_referee, playerID, start, end, true);
        case BuyDevelopmentCard.ID:
          return new BuyDevelopmentCard(_referee, playerID);
        case PlayMonopoly.ID:
          return new PlayMonopoly(_referee, playerID, actionJSON
              .get("resource").getAsString());
        case PlayYearOfPlenty.ID:
          return new PlayYearOfPlenty(_referee, playerID, actionJSON);
        case PlayKnight.ID:
          return new PlayKnight(_referee, playerID);
        case PlayRoadBuilding.ID:
          return new PlayRoadBuilding(_referee, playerID);
        case TradeWithBank.ID:
          return new TradeWithBank(_referee, playerID, actionJSON);
        case EndTurn.ID:
          return new EndTurn(_referee, playerID);
        case ProposeTrade.ID:
          return new ProposeTrade(_referee, playerID, actionJSON);
        case UpdateResource.ID:
          return new UpdateResource(_referee, playerID);
        default:
          String err = String.format("The action %s does not exist.", action);
          throw new IllegalArgumentException(err);
        }
      } catch (NullPointerException e) {
        e.printStackTrace();
        throw new IllegalArgumentException(
            "The JSON is missing a required parameter. Check documentation for more information.");
      }
    }
  }

  private IntersectionCoordinate toIntersectionCoordinate(JsonObject object) {
    JsonObject coord1 = object.get("coord1").getAsJsonObject();
    JsonObject coord2 = object.get("coord2").getAsJsonObject();
    JsonObject coord3 = object.get("coord3").getAsJsonObject();
    HexCoordinate h1 = new HexCoordinate(coord1.get("x").getAsInt(), coord1
        .get("y").getAsInt(), coord1.get("z").getAsInt());
    HexCoordinate h2 = new HexCoordinate(coord2.get("x").getAsInt(), coord2
        .get("y").getAsInt(), coord2.get("z").getAsInt());
    HexCoordinate h3 = new HexCoordinate(coord3.get("x").getAsInt(), coord3
        .get("y").getAsInt(), coord3.get("z").getAsInt());
    return new IntersectionCoordinate(h1, h2, h3);
  }

}
