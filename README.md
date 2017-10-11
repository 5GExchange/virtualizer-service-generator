### Building the project
(from the project root folder)
- ant clean 
- ant jar 

The Ant output will show the command line to be used to start the project, e.g.:
/usr/lib/jvm/java-8-openjdk-amd64/jre/bin/java -cp /tmp/service-generator/resty-0.3.2.jar:/tmp/service-generator/dist/VirtualizerServiceGenerator.jar virtualizerservicegenerator.VirtualizerServiceGenerator

### Starting the project 
(from the project root folder)


- To generate XML files (create/delete) to be used for the edit-config (no requests will be submitted, generated xml files will be saved in the ./xmls directory)
```sh
$ <java_path>/java -cp <project_path>/service-generator/resty-0.3.2.jar:<project_path>/service-generator/dist/VirtualizerServiceGenerator.jar virtualizerservicegenerator.VirtualizerServiceGenerator <Nchains> <chainSize> <SAPin> <SAPout>
```

- To generate and submit requests (create/delete) to the RO (will try using available SAPs from get-config)
```sh
$ <java_path>/java -cp <project_path>/service-generator/resty-0.3.2.jar:<project_path>/service-generator/dist/VirtualizerServiceGenerator.jar virtualizerservicegenerator.VirtualizerServiceGenerator <ROaddress> <ROport> <Nchains> <chainSize>
```

- To generate and/or submit requests according to the settings of the properties file specified as argument
```sh
$ <java_path>/java -cp <project_path>/service-generator/resty-0.3.2.jar:<project_path>/service-generator/dist/VirtualizerServiceGenerator.jar virtualizerservicegenerator.VirtualizerServiceGenerator <conf.properties>
```

An example of properties file is available in the project root folder (`conf.properties`):

```sh
orchestrator.address=localhost
orchestrator.port=8888
chains.number=5
chain.size=10
nodes.type=vlsp:router
sap.in=SAP0
sap.out=SAP1
submit=true
```
