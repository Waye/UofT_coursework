# multiAgents.py
# --------------
# Licensing Information:  You are free to use or extend these projects for
# educational purposes provided that (1) you do not distribute or publish
# solutions, (2) you retain this notice, and (3) you provide clear
# attribution to UC Berkeley, including a link to http://ai.berkeley.edu.
#
# Attribution Information: The Pacman AI projects were developed at UC Berkeley.
# The core projects and autograders were primarily created by John DeNero
# (denero@cs.berkeley.edu) and Dan Klein (klein@cs.berkeley.edu).
# Student side autograding was added by Brad Miller, Nick Hay, and
# Pieter Abbeel (pabbeel@cs.berkeley.edu).


from util import manhattanDistance
from game import Directions
import random, util
import math
from game import Agent


class ReflexAgent(Agent):
    """
      A reflex agent chooses an action at each choice point by examining
      its alternatives via a state evaluation function.

      The code below is provided as a guide.  You are welcome to change
      it in any way you see fit, so long as you don't touch our method
      headers.
    """

    def getAction(self, gameState):
        """
        You do not need to change this method, but you're welcome to.

        getAction chooses among the best options according to the evaluation function.

        Just like in the previous project, getAction takes a GameState and returns
        some Directions.X for some X in the set {North, South, West, East, Stop}
        """
        # Collect legal moves and successor states
        legalMoves = gameState.getLegalActions()
        # Choose one of the best actions
        scores = [self.evaluationFunction(gameState, action) for action in legalMoves]
        bestScore = max(scores)
        bestIndices = [index for index in range(len(scores)) if scores[index] == bestScore]
        chosenIndex = random.choice(bestIndices)  # Pick randomly among the best

        return legalMoves[chosenIndex]

    def evaluationFunction(self, currentGameState, action):
        """
        Design a better evaluation function here.

        The evaluation function takes in the current and proposed successor
        GameStates (pacman.py) and returns a number, where higher numbers are better.

        The code below extracts some useful information from the state, like the
        remaining food (newFood) and Pacman position after moving (newPos).
        newScaredTimes holds the number of moves that each ghost will remain
        scared because of Pacman having eaten a power pellet.

        Print out these variables to see what you're getting, then combine them
        to create a masterful evaluation function.
        """
        # Useful information you can extract from a GameState (pacman.py)
        successorGameState = currentGameState.generatePacmanSuccessor(action)
        newPos = successorGameState.getPacmanPosition()
        newFood = successorGameState.getFood()
        newGhostStates = successorGameState.getGhostStates()
        newScaredTimes = [ghostState.scaredTimer for ghostState in newGhostStates]

        "*** YOUR CODE HERE ***"

        # we consider two factors distance to ghost and distance to food
        # the shortest distance to food, the safest to eat food
        # the longest distance to ghost, the safest to move

        # find shortest distance to food
        width = newFood.width
        height = newFood.height
        min = float("inf")
        for i in range(width):
            for j in range(height):
                if newFood.data[i][j]:
                    dist = manhattanDistance(newPos, (i, j))
                    if dist < min:
                        min = dist



        # find longest distance to ghost
        ghostScore = 0
        # pac man is in invincible state, leave one 1 second to decide escape if too close to ghost
        if max(newScaredTimes) > 1:
            ghostScore = 0
        else:
            for ghost in newGhostStates :
                dist = manhattanDistance(newPos, ghost.getPosition())

                # If pacman and ghost too close, closer than 3, then pacman decides to escape
                if dist < 3:
                    ghostScore = float("inf")
                    break
                ghostScore += dist

        # conclude the score
        if ghostScore == float("inf"):
            ghostScore = ghostScore
        elif ghostScore == 0:
            ghostScore = 0
        else:
            ghostScore = 1.618/ghostScore

        foodScore = 1.618/min

        return successorGameState.getScore() + foodScore - ghostScore



class MultiAgentSearchAgent(Agent):
    """
      This class provides some common elements to all of your
      multi-agent searchers.  Any methods defined here will be available
      to the MinimaxPacmanAgent, AlphaBetaPacmanAgent & ExpectimaxPacmanAgent.

      You *do not* need to make any changes here, but you can if you want to
      add functionality to all your adversarial search agents.  Please do not
      remove anything, however.

      Note: this is an abstract class: one that should not be instantiated.  It's
      only partially specified, and designed to be extended.  Agent (game.py)
      is another abstract class.
    """

    def __init__(self, evalFn='scoreEvaluationFunction', depth='2'):
        self.index = 0  # Pacman is always agent index 0
        self.evaluationFunction = util.lookup(evalFn, globals())
        self.depth = int(depth)



def Compare(currentAgent,currentValue,newValue):
    if currentAgent == 0:
        return currentValue < newValue
    else:
        return currentValue > newValue


class MinimaxAgent(MultiAgentSearchAgent):
    """
      Your minmax agent (question 2)
    """

    def getAction(self, gameState):
        """
          Returns the minmax action from the current gameState using self.depth
          and self.evaluationFunction.
          Here are some method calls that might be useful when implementing minmax.

          gameState.getLegalActions(agentIndex):
            Returns a list of legal actions for an agent
            agentIndex=0 means Pacman, ghosts are >= 1

          gameState.generateSuccessor(agentIndex, action):
            Returns the successor game state after an agent takes an action

          gameState.getNumAgents():
            Returns the total number of agents in the game
        """
        def nextMove (state,currentDepth =0, currentAgent = 0):

            # initial action
            Action = None

            # set temp value for pacman and ghost
            pacman_temp_val = float("-inf")
            ghost_temp_val = float("inf")

            # the situation that ends game already
            # reach the limited depth
            if currentDepth == self.depth*state.getNumAgents():
                return self.evaluationFunction(state), Action
            # reach game result
            if state.isWin() or state.isLose():
                return self.evaluationFunction(state),Action



            # get successor
            for legalAction in state.getLegalActions(currentAgent):
                nextState = state.generateSuccessor(currentAgent, legalAction)
                newVal, newAction = nextMove(nextState, currentDepth+1, (currentAgent+1) % state.getNumAgents())

                if currentAgent == 0:
                    if Compare(currentAgent, pacman_temp_val, newVal):
                        pacman_temp_val = newVal
                        Action = legalAction
                        val = pacman_temp_val

                else:
                    if Compare(currentAgent, ghost_temp_val, newVal):
                        ghost_temp_val = newVal
                        Action = legalAction
                        val = ghost_temp_val

            return val, Action

        reVal, reAction = nextMove(gameState)
        return reAction


class AlphaBetaAgent(MultiAgentSearchAgent):
    """
      Your minimax agent with alpha-beta pruning (question 3)
    """

    def getAction(self, gameState):
        """
          Returns the minimax action using self.depth and self.evaluationFunction
        """
        "*** YOUR CODE HERE ***"

        def nextMove(state, alpha=float("-inf"), beta=float("inf"), currentDepth=0, currentAgent=0):

            # initial action
            Action = None

            # set temp value for pacman and ghost
            pacman_temp_val = float("-inf")
            ghost_temp_val = float("inf")

            # the situation that ends game already
            # reach the limited depth
            if currentDepth == self.depth * state.getNumAgents():
                return self.evaluationFunction(state), Action
            # reach game result
            if state.isWin() or state.isLose():
                return self.evaluationFunction(state), Action


            for legalAction in state.getLegalActions(currentAgent):
                sucState = state.generateSuccessor(currentAgent, legalAction)
                newVal, newAction = nextMove(sucState, alpha, beta, currentDepth + 1, (currentAgent + 1) % state.getNumAgents())

                # check availiable values
                if currentAgent == 0:
                    if Compare(currentAgent, pacman_temp_val, newVal):
                        pacman_temp_val = newVal
                        Action = legalAction
                        val = pacman_temp_val

                else:
                    if Compare(currentAgent, ghost_temp_val, newVal):
                        ghost_temp_val = newVal
                        Action = legalAction
                        val = ghost_temp_val

                # start pruning
                if currentAgent:
                    beta = min(beta, val)
                    if val <= alpha:
                        return val, Action
                else:
                    alpha = max(alpha, val)
                    if val >= beta:
                        return val, Action
            return val, Action

        reVal, reAction = nextMove(gameState)
        return reAction

class ExpectimaxAgent(MultiAgentSearchAgent):
    """
      Your expectimax agent (question 4)
    """

    def getAction(self, gameState):
        """
          Returns the expectimax action using self.depth and self.evaluationFunction
          All ghosts should be modeled as choosing uniformly at random from their
          legal moves.
        """

        def nextMove (state,currentDepth =0, currentAgent = 0):

            # initial action
            Action = None

            # set temp value for pacman and ghost
            # set temp value for pacman and ghost
            pacman_temp_val = float("-inf")
            ghost_temp_val = 0

            # the situation that ends game already
            # reach the limited depth
            if currentDepth == self.depth * state.getNumAgents():
                return self.evaluationFunction(state), Action
            # reach game result
            if state.isWin() or state.isLose():
                return self.evaluationFunction(state), Action

            # get successor
            for legalAction in state.getLegalActions(currentAgent):
                nextState = state.generateSuccessor(currentAgent, legalAction)
                newVal, newAction = nextMove(nextState, currentDepth + 1, (currentAgent + 1) % state.getNumAgents())

                # ghost choice
                if currentAgent != 0:
                    ghost_temp_val += newVal
                    val = ghost_temp_val

                # pac man choice
                else:
                    if Compare(0, pacman_temp_val, newVal):
                        pacman_temp_val = newVal
                        Action = legalAction
                        val = pacman_temp_val

            return val, Action

        reVal, reAction = nextMove(gameState)
        return reAction

def scoreEvaluationFunction(currentGameState):
    """
      This default evaluation function just returns the score of the state.
      The score is the same one displayed in the Pacman GUI.

      This evaluation function is meant for use with adversarial search agents
      (not reflex agents).
    """
    return currentGameState.getScore()



def betterEvaluationFunction(currentGameState):
    """
      Your extreme ghost-hunting, pellet-nabbing, food-gobbling, unstoppable
      evaluation function (question 5).
    """
    "*** YOUR CODE HERE ***"
    # Get all useful data
    pos = currentGameState.getPacmanPosition()
    food = currentGameState.getFood()
    ghostStates = currentGameState.getGhostStates()
    scaredTime = [ghostState.scaredTimer for ghostState in ghostStates]
    capsules = currentGameState.getCapsules()

    # Factor 1: pacman eats capsule
    mCapsule = float("inf")
    for capsilePos in capsules:
        dist = manhattanDistance(capsilePos, pos)
        if dist < mCapsule:
            mCapsule = dist
    if mCapsule != float("inf"):
        capsuleFactor = 1.0 / (mCapsule + 1)
    else:
        capsuleFactor = 0

    # Factor 2: find smallest dist between pac man and food
    width = food.width
    height = food.height
    minFood = float("inf")
    for i in range(width):
        for j in range(height):
            if food.data[i][j]:
                dist = manhattanDistance(pos, (i, j))
                if dist < minFood:
                    minFood = dist
    if minFood != float("inf"):
        foodDistFactor = 1.0 / (minFood + 1)
    else:
        foodDistFactor = float("inf")



    # Factor 3: pacman chase scared ghost
    scaredghost = 0
    for timer in range(len(scaredTime)):
        dist = manhattanDistance(pos, ghostStates[timer].getPosition())
        if scaredTime[timer] != 0 and dist > 0:
            scaredghost += 300.0 / dist
    scaredghostFactor = scaredghost


    # Factor 4: pac man avoid ghost if not in scared time
    sumgo = 0
    for Index in range(len(ghostStates)):
        if scaredTime[Index] == 0:
            ghostPosition = ghostStates[Index].getPosition()
            dist = manhattanDistance(pos, ghostPosition)
            if dist > 0:
                sumgo += 1.0 / dist
    if sumgo is not None:
        ghostFactor = sumgo
    else:
        ghostFactor = - float("inf")

    return currentGameState.getScore() + foodDistFactor - ghostFactor*10 + scaredghostFactor + capsuleFactor





# Abbreviation:
better = betterEvaluationFunction
