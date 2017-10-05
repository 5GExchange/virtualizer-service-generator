### Builind the project
(from the project root folder)
- ant clean 
- ant jar 

### Starting the project 
(from the project root folder)

- To generate XML files (create/delete) to be used for the edit-config (no submission)
```sh
$ java -jar dist/VirtualizerServiceGenerator.jar <Nchains> <chainSize> <SAPin> <SAPout>
```

- To generate and submit requests (create/delete) to the RO (will try using SAPs from get-config)
```sh
$ java -jar dist/VirtualizerServiceGenerator.jar <ROaddress> <ROport> <Nchains> <chainSize>
```

- To generate and/or submit requests according to the settings of the properties file
```sh
$ java -jar dist/VirtualizerServiceGenerator.jar <conf.properties>
```

An example of properties file is available in the project root folder and is called conf.properties:

> orchestrator.address=localhost
orchestrator.port=8888
chains.number=5
chain.size=10
nodes.type=vlsp:router
sap.in=SAP0
sap.out=SAP1
submit=true
