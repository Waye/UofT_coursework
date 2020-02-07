#   Look for #IMPLEMENT tags in this file. These tags indicate what has
#   to be implemented to complete the warehouse domain.

#   You may add only standard python imports---i.e., ones that are automatically
#   available on TEACH.CS
#   You may not remove any imports.
#   You may not import or otherwise source any of your own files

import os  # for time functions
from search import *  # for search engines
from sokoban import SokobanState, Direction, PROBLEMS  # for Sokoban specific classes and problems


def sokoban_goal_state(state):
    '''
  @return: Whether all boxes are stored.
  '''
    for box in state.boxes:
        if box not in state.storage:
            return False
    return True


def heur_manhattan_distance(state):
    # IMPLEMENT
    '''admissible sokoban puzzle heuristic: manhattan distance'''
    '''INPUT: a sokoban state'''
    '''OUTPUT: a numeric value that serves as an estimate of the distance of the state to the goal.'''
    # We want an admissible heuristic, which is an optimistic heuristic.
    # It must never overestimate the cost to get from the current state to the goal.
    # The sum of the Manhattan distances between each box that has yet to be
    # stored and the storage point nearest to it is such a heuristic.
    # When calculating distances, assume there are no obstacles on the grid.
    # You should implement this heuristic function exactly, even if it is tempting to improve it.
    # Your function should return a numeric value; this is the estimate of the distance to the goal.

    dist_sum = 0
    for box in state.boxes:  # Find nearest storage point to box that is not in restrictions list
        dist_min = 2 ** 31
        for store in state.storage:
            new_dist = (abs(box[0] - store[0]) + abs(
                box[1] - store[1]))  # Calculate manhattan distance between box and storage point
            if new_dist < dist_min:
                dist_min = new_dist
        dist_sum += dist_min

    return dist_sum


# SOKOBAN HEURISTICS
def trivial_heuristic(state):
    '''trivial admissible sokoban heuristic'''
    '''INPUT: a sokoban state'''
    '''OUTPUT: a numeric value that serves as an estimate of the distance of the state (# of moves required to get) to the goal.'''
    count = 0
    for box in state.boxes:
        if box not in state.storage:
            count += 1
    return count


#================================================




def heur_alternate(state):
    # IMPLEMENT
    '''a better heuristic'''
    '''INPUT: a sokoban state'''
    '''OUTPUT: a numeric value that serves as an estimate of the distance of the state to the goal.'''
    # heur_manhattan_distance has flaws.
    # Write a heuristic function that improves upon heur_manhattan_distance to estimate distance between the current state and the goal.
    # Your function should return a numeric value for the estimate of the distance to the goal.

    heur = 0
    # if there is a deadlock return "inf" value
    if check_remain(state):
        return float("inf")
    # else calculate heurvalue
    heur += box_to_dest(state)
    heur += rob_to_box(state)
    return heur


def check_remain(state):
    # check corner and edge
    for box in state.boxes:
        storage_remainitions = get_remain_storage(box,state)
        if box not in storage_remainitions:
            if check_corner(box, state):return True
            if check_edge(box,state): return True
    return False


def check_corner(box_pos,state):
    # check corner deadlock including box and obstacle make up corner deadlock
    obst_list = state.obstacles|state.boxes
    up= (box_pos[0],box_pos[1]+1)
    down= (box_pos[0],box_pos[1]-1)
    left= (box_pos[0]-1,box_pos[1])
    right= (box_pos[0]+1,box_pos[1])

    # corner made by wall and obstacles
    if (box_pos[0] ==0 or box_pos[0] == state.width - 1) and \
            (box_pos[1] == 0 or box_pos[1] == state.height - 1 or up in obst_list or down in obst_list):
        return True
        return False
    # corner made all by obstacle
    if ((up or down) and (left or right)) in state.obstacles:return True
    return False


def check_edge(box_pos,state):
    # deadlock:if no storage on that edge 
    storage_remain = get_remain_storage(box_pos,state)
    for storage in storage_remain:
        if (box_pos[0]== storage[0] == (0 or state.width-1)) or (box_pos[1]== storage[1]== (0 or state.height-1)):
            return True

    return False




def get_remain_storage(box, state):
    #get remain storage
    remain = []
    for place in state.storage:
        remain.append(place)
    if box in remain:
        return [box]
    for other_boxes in state.boxes:
        if box != other_boxes:
            if other_boxes in remain:
                remain.remove(other_boxes)
    return remain


def box_to_dest(state):
    # distance between box to storage
    cost=0

    for box in state.boxes:
        remain_positions= get_remain_storage(box,state)
        cost_each_box = float("inf")
        for possibility in remain_positions:
            current_cost = md(possibility,box)+ optimization(box,possibility,state)*2
            if current_cost<cost_each_box:
                cost_each_box=current_cost
        cost += cost_each_box
    return cost


def rob_to_box(state):
    # distance between rob to box
    cost =0

    for rob in state.robots:
        closest = float("inf")
        for box in state.boxes:
            if (md(box,rob)+optimization(rob,box,state)*2)<closest:
                closest= md(box,rob)+optimization(rob,box,state)*2
        cost += closest
    return cost


def md(x,y):
    #return the manhattan distance between x and y
    return abs(x[0]-y[0])+abs(x[1]-y[1])


def optimization(ori,dest,state):
    # if there is a obstacle in our searching area which satisfy below character, we will add those 1
    total=0
    all_obs= state.obstacles|frozenset(state.robots)
    for obst in all_obs:
        if max(dest[0],ori[0]) > obst[0] > min(ori[0],dest[0]):
            if max(dest[1],ori[1])>obst[1]> min(ori[1],dest[1]):
                total+=1
    return total









#===================================================

def heur_zero(state):
    '''Zero Heuristic can be used to make A* search perform uniform cost search'''
    return 0


def fval_function(sN, weight):
    # IMPLEMENT
    """
    Provide a custom formula for f-value computation for Anytime Weighted A star.
    Returns the fval of the state contained in the sNode.

    @param sNode sN: A search node (containing a SokobanState)
    @param float weight: Weight given by Anytime Weighted A star
    @rtype: float
    """

    # Many searches will explore nodes (or states) that are ordered by their f-value.
    # For UCS, the fvalue is the same as the gval of the state. For best-first search, the fvalue is the hval of the state.
    # You can use this function to create an alternate f-value for states; this must be a function of the state and the weight.
    # The function must return a numeric f-value.
    # The value will determine your state's position on the Frontier list during a 'custom' search.
    # You must initialize your search engine object as a 'custom' search engine if you supply a custom fval function.
    return sN.gval + weight * sN.hval


def anytime_weighted_astar(initial_state, heur_fn, weight=1., timebound=10):
    '''Provides an implementation of anytime weighted a-star, as described in the HW1 handout'''
    '''INPUT: a sokoban state that represents the start state and a timebound (number of seconds)'''
    '''OUTPUT: A goal state (if a goal is found), else False'''
    '''implementation of weighted astar algorithm'''

    start_time = os.times()[0]
    remainig_time = timebound

    se = SearchEngine('custom', 'full')
    se.init_search(initial_state, sokoban_goal_state, heur_fn, (lambda sN: fval_function(sN, weight)))
    best_cost = float("inf")
    # first search
    result = se.search(remainig_time, (float("inf"), float("inf"), best_cost))
    remaining_time = timebound - (os.times()[0] - start_time)
    if result is False:
        return False
    else:
        best_cost = result.gval + heur_fn(result)

    # when time is remaining
    while remainig_time > 0:
        best_result = se.search(remainig_time, (float("inf"), float("inf"), best_cost))
        remaining_time = timebound - (os.times()[0] - start_time)

        if best_result:
            best_cost = best_result.gval + heur_fn(best_result)
            result = best_result
        else:
            break

    return result


def anytime_gbfs(initial_state, heur_fn, timebound=10):
    # IMPLEMENT
    '''Provides an implementation of anytime greedy best-first search, as described in the HW1 handout'''
    '''INPUT: a sokoban state that represents the start state and a timebound (number of seconds)'''
    '''OUTPUT: A goal state (if a goal is found), else False'''
    '''implementation of weighted astar algorithm'''

    start_time = os.times()[0]
    remainig_time = timebound

    se = SearchEngine('best_first', 'default')
    se.init_search(initial_state, sokoban_goal_state, heur_fn)
    best_cost = float("inf")
    # first search
    result = se.search(remainig_time, (best_cost, float("inf"), float("inf")))
    remaining_time = timebound - (os.times()[0] - start_time)
    if result is False:
        return False
    else:
        best_cost = result.gval

    # when time is remaining
    while remainig_time > 0:
        best_result = se.search(remainig_time, (best_cost, float("inf"), float("inf")))
        remaining_time = timebound - (os.times()[0] - start_time)

        if best_result.gval < best_cost:
            best_cost = best_result.gval
            result = best_result
        else:
            break

    return result
