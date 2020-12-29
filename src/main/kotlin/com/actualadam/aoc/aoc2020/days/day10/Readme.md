# Notes

Part 1 came super fast. There's a prescribed way to put together a single adapter array. Whenever there are multiple
options the fit the 3j (jolts) or less difference criterion, you pick the smallest joltage difference. Count up the 1j
and 3j differences and multiply them for the answer.

Part 2 was very difficult for me. I tried this idea of getting a brute force approach that where I was confident with
the solution and then try to optimize it.

* Build an adjacency list from all the adapters. An adapter is adjacent to another if it is rated 1 to 3 jolts greater.
  Transpose the adjacency list and walk the graph depth first. When a path is completely walked to the end, save the
  path to an array. Then count the paths and that's the answer. First, I didn't need to transpose the graph. I think I
  just really wanted to because I was still in the mindset of the bag problem on day 7. Second, I ran out of heap
  because I was actually saving each path. Wasn't even thinking about that. I expected I would overflow the stack first
  because I couldn't figure out a tail recursive way to express the algorithm.
* Tried without transposing. Same result of backwards or forwards.
* Tried just keeping a count variable instead of saving the paths. At this point I believe I had a correct algorithm and
  no memory issues, but it would not complete in a reasonable amount of time. The instructions warned about the
  impracticality of this approach, so no surprise there.
* I started researching ways to reduce the number of calculations. I didn't know exactly what I was looking for, but I
  was aware that the algorithm I was using was doing more work than was necessary to solve the problem. I had seen
  memoized functions before and knew there was a FP library for Kotlin (Arrow) that provided function memoization. I
  figured out that I could change my brute force algorithm to do something worth memoizing: keep the graph as-is, but
  start with the highest joltage key and path to the end, then the next highest and path to the end, etc. Then I could
  memoize the number of paths from any vertex to the destination max joltage vertex, and not have to actually walk EVERY
  SINGLE PATH. I just couldn't figure out how to use the library memoization recursively. I had things set up with an
  outer function that held some local state and helper function that did the recursion. The memoization was outside the
  scope of the recursive call. So I could only use the memoized call on the initial call to the helper. ug.
* At this point I resorted to cheating. I found a python post on the solution megathread on reddit. This solution didn't
  exactly build a graph, it just filtered the adapter list on each iteration, essentially finding what would be adjacent
  vertices in my structure on the fly. This didn't actually help me find the solution I needed, but it's why that's
  algorithm is part of my current solution instead of building the graph up front and walking it.
* Having validation on the brute force part of my approach. I decided to just implement my cache manually. I put a map
  of computations in my outside function and tried to computeIfAbsent inside the helper. This throws a
  ConcurrentModificationException because you are recursively calling computeIfAbsent on the map as part of the "
  compute", which is explicitly called out in the documentation of ConcurrentHashMap, which is apparently the underlying
  structure of a Kotlin MutableMap.
* Did the by hand by hand by just old-school checking for the key in the collection and if it doesn't exist, calling the
  function and caching the result. This gave me a correct answer. I would be paranoid about using a solution like this
  in production code (which is honestly why I'm writing these notes), because I still don't understand why I should feel
  safe doing the recursive modification by hand when `computeIfAbsent` refuses to allow it. I couldn't figure out how to
  use the library memoization in a recursive fashion. 

