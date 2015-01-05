mms-client
==========

Simple implementation of a multi-threaded Java MMS client. 
It connects to the MMS server, retrieve the list of hosts in the provided group, and then iterate over the list of metrics provided by each of them and get the last value. 


Running
=======

- Change the parameters in mmsclient.properties
- Run with "mvn exec:java" for one execution or  "mvn exec:java -Drepeat=20" to loop 

