## Selection 
Top-K problem: ```O(N)```
## Duplicated Keys 
Merge Sort - Always between ```NlogN``` and ```1/2NLogN``` compares
QSort - ```N^2``` when no distinct items. => 3-way partition.
- move all elements to the same side
- ignore item for partition => ```NlogN``` compares
- three-way partition => ```N``` compares
## System Sort
- Merge Sort for reference and QSort for primitive types
  - Merge Sort is stable and guarantee Nlog(N)
  - QSort is of better performance
  - Random Shuffle matters

## Questions
###  Decimal dominants. Given an array with nn keys, design an algorithm to find all values that occur more than n/10n/10 times. The expected running time of your algorithm should be linear.
Perhaps the hint was aiming at the following approach. Suppose that the array were sorted. If a value appears m times and we divide the array into intervals of length m, then the value must appear at the end of one of the intervals. This suggests the following algorithm for finding all values that appear n/C times: compute the i⋅n/C-largest elements for 1≤i≤C, and for each of them, check whether the value appears at least n/C times. Each iteration takes O(n) time using QuickSelect, for a total of O(Cn) time.