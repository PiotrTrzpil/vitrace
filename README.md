vitrace
=======
(Incubating)

An idea for a tool for visualizing log files in a way to improve bug tracking and monitoring of an application.
It strives to improve log message visibility and ease of understanding complex events.


Features
--------

- 2D space: Text log files are parsed and presented as entities on a two-dimensional space. 
- Common representation for different log entry formats' informations
- A try to maximize visibility of interesting data


Road map & ideas
================

If methods can be recognized, they can presented separately, grouping contained logs and are connected using lines.
Colors are used to represent different log levels, threads


Cuts:
- by thread
- by time
- by call sequence

Views:
- basic - flat + columns for different threads
- overview - an easy to read view, grouping certain events, mostly statistical, no linear connection to time
- by method - focus on one method and calls leading to it
- by method invocation - focus on one call
- detail - full detail
- folding certain methods

Info dimensions:
- time
- thread
- log level
- loggerName
- message

For tracing:
- class
- method
- method parameters
- method return value


Visualizers:

- date
- time - 
- level (color) - 
- loggerName - 
- thread - 
- JSON - 

Filtering & Highlighting
- grep-like


Variable sizes
- by log level
- by thread
- by loggerName

