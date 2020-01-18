# Graph / Dijkstra 알고리즘 2015.10


OOP 2 수업에서 Graph를 구현하고 BFS, DFS, Dijkstra 알고리즘을 실습하였습니다.

---

## <프로젝트 개요>

- 개발인원: 1명 (본인)
- 개발기간: 2015.11.17 ~ 2015.11.28 (12일)
- 개발환경: Eclipse
- 개발언어: JAVA
- 세부사항:
    - Graph는 다음의 두 맵을 포함함
        - HashMap<String, HashMap<String, Integer>> adjacencyMap
        - HashMap<String, E> dataMap
    - DFS, BFS, Dijkstra 공통적으로 HashSet을 사용하여 Visited node check
        - DFS
            - Stack을 사용하여 backtrack (LIFO)
        - BFS
            - Queue을 사용하여 순서대로 처리 (FIFO)
        - Dijkstra
            - Map<K, ArrayList<K>>으로 K node부터 다른 노드까지 걸리는 path 관리
            - TreeMap<K, V> 로 origin부터 각 node까지 걸리는 cost 관리
    - CallBack 인터페이스 사용
        - String 을 result로 갖는 콜백 함수를 만들고, DFS, BFS, Dijkstra에서 traversing한 결과를 콜백 함수를 불러서 콜백 함수가 전달받은 node를 프로세싱하며 result에 추가함.
- Junit으로 테스트 한 모습

        @Test
        public void testDijkstras1() {
        		Graph<Double> graph = new Graph<Double>();
        		String[] vertices = new String[] { "ST", "A", "B", "C", "D" };
        		for (int i = 0; i < vertices.length; i++) {
        			graph.addVertex(vertices[i], new Double(i + 0.001));
        		}
        
        		graph.addDirectedEdge("ST", "A", 5);
        		graph.addDirectedEdge("ST", "B", 8);
        		graph.addDirectedEdge("A", "C", 10);
        		graph.addDirectedEdge("B", "C", 3);
        		graph.addDirectedEdge("A", "B", 1);
        		graph.addDirectedEdge("C", "D", 9);
        
        		String startVertex = "ST";
        		String answer = runDijkstras(graph, startVertex);
        
        		//주어진 TestingSupport 파일로 answer을 변환하여 예상 output파일과 비교
        		answer += TestingSupport.hardCodingPrevention;
        		
        				assertTrue(TestingSupport.correctResults("studentTestDijkstras1.txt", answer));
        		}

---
