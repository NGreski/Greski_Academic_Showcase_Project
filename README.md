# Algorithms Final Project  

**Author:** Noah Greski  
**Date:** April 29, 2024  

## Overview  
This project explores two areas of algorithm analysis:  
1. **Rubik’s Cube Solving Algorithms (2x2 Cube)**  
2. **Modified Dijkstra’s Algorithm with Simulated Sidewalk Data (METAL graphs)**  

The goal was to compare algorithm efficiency in both problem domains and draw conclusions about performance in a programmatic environment versus real-world scenarios.  

---

## Part 1: Rubik’s Cube Algorithms  

Two solving methods were studied:  
- **X1 (Layers):** Solves the cube layer by layer.  
- **X2 (Corners):** Old Pochmann method, repeatedly swaps corners.  

### Testing  
- Implemented in Java.  
- Ran 20 trials each with 10, 100, and 1000 solves (120 executions total).  
- Metrics: **average moves** and **elapsed time (ms)**.  

### Results  
- **Moves:** X1 averaged ~40 moves vs. X2’s ~487 moves → X1 more move-efficient.  
- **Time:** X1 averaged ~7.5ms vs. X2’s ~1.7ms → X2 faster in execution.  
- **Correlation:** X1 time strongly linked to moves; X2 less dependent.  

**Conclusion:**  
- In code, **X2 (Corners)** is more efficient (faster runtime).  
- In real life, **X1 (Layers)** is superior (fewer moves to perform).  

---

## Part 2: Modified Dijkstra’s Algorithm (Sidewalk Data)  

- Standard Dijkstra’s Algorithm was modified to only consider edges within **2 miles** to simulate “roads with sidewalks.”  
- Tested with METAL road graphs (e.g., Siena 50-mile radius).  
- Example: Siena → Scotia path found (~35 miles).  

**Limitations:**  
- Data is simulated; real sidewalk data is needed for accuracy.  
- Some routes failed due to restrictions on allowable edges.  

---

## Key Takeaways  
- **Rubik’s Cube:** Efficiency depends on context — runtime (computer) vs. move count (real world).  
- **Sidewalk Shortest Paths:** Simulation shows potential, but real sidewalk data would improve results.  
