
public class Solution {

  /*
  Distance between directly connected ports is always '1'.
  With 100 ports, maximum indirect distance is 100x1.
  Thus, when there is not a path(direct or indirect) between two ports
  we put a value '1000'.
  */
  public final int INFINITY = 1000;
  public int[][] shortestDistance;
  public int[][] ports;

  /*
  By the problem design on binarysearch.com, we have to work
  around the given method 'public int solve(int[][] ports, int[][]shipments)'
  so that the code can be run on the website. Even though the name 'solve'
  does not make a lot of sense, it is left as it is, so that the code can
  be run directly on the website, without any modifications.
  */
  public int solve(int[][] ports, int[][] shipments) {

    this.ports = ports;
    initialize_shortestDistance();
    floydWarshall_findShortestDistanceBetweenPorts();

    return findTotalCost_forAllShipments(shipments);
  }

  public int findTotalCost_forAllShipments(int[][] shipments) {

    int totalCostForAllShipments = 0;
    for (int r = 0; r < shipments.length; r++) {

      int start = shipments[r][0];
      int end = shipments[r][1];

      // If there is no path between two ports, direct or indirect, the cost for this shipment is '0'.
      if (shortestDistance[start][end] < INFINITY) {
          totalCostForAllShipments += shortestDistance[start][end];
      }
    }

    return totalCostForAllShipments;
  }

  /*
  Floyd Warshall Algorithm: fill the matrix 'shortestDistance' with the shortest paths between ports,
                            when such a path exists.
  */
  public void floydWarshall_findShortestDistanceBetweenPorts() {

    for (int middle = 0; middle < shortestDistance.length; middle++) {

      for (int start = 0; start < shortestDistance.length; start++) {

        for (int end = 0; end < shortestDistance[start].length; end++) {

          if (shortestDistance[start][end] > shortestDistance[start][middle] + shortestDistance[middle][end]) {

            shortestDistance[start][end] = shortestDistance[start][middle] + shortestDistance[middle][end];
          }
        }
      }
    }
  }

  public void initialize_shortestDistance() {

    shortestDistance = new int[ports.length][ports.length];

    for (int r = 0; r < shortestDistance.length; r++) {
      for (int c = 0; c < shortestDistance.length; c++) {
        if (r != c) {
          shortestDistance[r][c] = INFINITY;
        }
      }
    }

    for (int r = 0; r < ports.length; r++) {
      for (int c = 0; c < ports[r].length; c++) {
        shortestDistance[r][ports[r][c]] = 1;
      }
    }
  }
}
