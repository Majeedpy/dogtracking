<snippet>
  <content>
# Dog Tracking App
This project is created to track dogs on the field. It provides functionalities for creating a new dog resource, view the dogs on a map viewer and view dog clusters based on their weights using a K-means clustring algorithm.
## Installation
To install this project, download the source code, open it with and IDE and start working with it. You can export the java project as a war file and deploy it in your application.
## Usage
The project can be used from the client web site created: 
http://dogtracking-majeedapps.rhcloud.com/

The web services that are supported by this app include:

Get all dogs: 
http://dogtracking-majeedapps.rhcloud.com/dogs

Get a certain dog: 
http://dogtracking-majeedapps.rhcloud.com/dog/{id}

Delete a dog: 
http://dogtracking-majeedapps.rhcloud.com/delete/{id}

Get Clusters: 
http://dogtracking-majeedapps.rhcloud.com/dogClusters?k={k}

Create a dog:
http://dogtracking-majeedapps.rhcloud.com/dogs/insert/{weight}/{name}/{heartBeat}/{temperature}/{latitude}/{longitude}

## Contributing
1. Fork it!
2. Create your feature branch: `git checkout -b my-new-feature`
3. Commit your changes: `git commit -am 'Add some feature'`
4. Push to the branch: `git push origin my-new-feature`
5. Submit a pull request 

## History
Project created

## Credits
Development done by Majeed Pooyandeh

## License
Free

</content>
</snippet>
