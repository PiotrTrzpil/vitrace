Vitrace
(using http://kenmcdonald.github.io/rex/ internally)
=======
(Incubating)

A programmer tool for visualizing log files in order to improve bug tracking and monitoring of an application.
It aims to improve log message visibility and ease of understanding complex events.

Vision:
--------
For a long time log file has been used as a tool for diagnostics and troubleshooting the system in operation. However, I find it rather demotivating that most are still read by humans as text, searched by grep and analized by hand and eye.
Tools such as Logstash and Elasticsearch are used mostly for data extraction and statistics. They also require lots of configuration. I think there is still much room for improvement in a craft of hunting and diagnosing a bug based on logs.

The idea that drives Vitrace is based on a fact, that most log entries have similar structure, slightly different representation and greatly varied importance. Even certain entries parts can have much greater importance for a programmer, that others. If we could abstract over the text representation, achieve greater visual distiction between certain information, we may ease the task of understanding the internal workings of a system. Certain entries could be hidden, merged with others or joined in groups.

TODO:
--------

- 2D space: Text log files are parsed and presented as entities in a two-dimensional space. 
- A Common representation for different log entry formats' information.
- A strive for maximizing the visibility of interesting data.


Road map & ideas
================

Certain text can be hidden, shown on demand.
If methods can be recognized, they can presented separately, grouping contained logs and are connected using lines.
Colors are used to represent different log levels, threads.


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

Filtering and Highlighting
- grep-like


Variable sizes
- by log level
- by thread
- by loggerName

