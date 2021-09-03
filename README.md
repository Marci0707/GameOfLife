# GameOfLife
Conway's game of life is a cell simulation where cells may die or come back to life in every generation. Their fate is decided by the number of living neighbours.
You can read more about Conway's Game Of Life here: https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life

![start](https://user-images.githubusercontent.com/78796219/132031577-66f7f49a-305f-43b1-89a4-c0079bb8618b.png)

In this implementation you can set the following things:

-The height and width of the field

-A ceratin p probability which determines the chance whether a cell starts as a live cell in the 0th generation

-The pace of the simulation; 15/70/1000ms for a generation

-Custom colors for live and dead cells

-wheter to show the grid of the field with lines

You also can request a colorcoded graph which, after ending the simulation, shows how the distribution of the live and dead cells changed over time.Just check the box in the "View-get graph" option

![graph](https://user-images.githubusercontent.com/78796219/132031587-b1b0571e-6316-49c7-9590-d4643b45a2ad.png)


You may save the current position to a binary file or load on of your custom positions.I uploaded a smiley face starting position in the source files but you can easily create your own.Just describe the cells row by row with 1s and 0s for live and dead cells but do make sure to create a rectangular field in the end.


