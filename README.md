# GameOfLife
About Conway's Game Of Life you can read more here:https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life

![start](https://user-images.githubusercontent.com/78796219/132030502-f26c98f1-e802-487e-81ea-3d7bbebbbe49.png)


In this implementation you can set the following things:

-The height and width of the field
-A ceratin p probability which determines the chance whether a cell starts as a live cell in the 0th generation
-The pace of the simulation; 15/70/1000ms for a generation
-Custom colors for live and dead cells
-wheter to show the grid of the field

You also can request a graph which after stopping a simulation shows how the distribution of the live and dead cells changed over time.
![graph](https://user-images.githubusercontent.com/78796219/132030067-87a7751b-92e4-4cee-8289-9eeb002a857f.png)

You may save the current position to a binary file or load on of your custom positions.I uploaded a smiley face starting position in the source files. Just describe the cells row by row with 1s and 0s for a live and dead cell but do make sure to create rectangular field


