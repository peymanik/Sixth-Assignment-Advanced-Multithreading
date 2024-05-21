# Algorithms for Calculating π

## Leibniz Formula

The first algorithm that I used was the [Leibniz formula](https://en.wikipedia.org/wiki/Leibniz_formula_for_%CF%80). This formula was simple but couldn't calculate last floatingPoint numbers accurately. Even if I calculated up to 2<sup>17</sup> elements of the series, it could only pass the first test.

## Spigot Algorithm of Rabinowitz and Wagon

The second algorithm was the [Spigot Algorithm of Rabinowitz and Wagon](https://www.cut-the-knot.org/Curriculum/Algorithms/SpigotForPi.shtml). This algorithm was better than the previous one and could pass all tests. However, the downside is that it is very slow. I had to increase the maximum value of i of sigma up to 16,000 to pass the last test.

## Chudnovsky Algorithm

The last algorithm was the [Chudnovsky algorithm](https://en.wikipedia.org/wiki/Chudnovsky_algorithm#:~:text=The%20Chudnovsky%20algorithm%20is%20a,on%20Ramanujan's%20%CF%80%20formulae), which is considered the fastest and most optimized algorithm that exists. It was able to pass all tests by calculating only 80 elements of its series. You can find more info about the algorithm [here](https://observablehq.com/@galopin/the-chudnovsky-algorithm-for-calculating-pi).
