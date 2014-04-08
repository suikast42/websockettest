websockettest
=============

Deployable on Wildfdfly 8.0.0.Final. Other servers not tested.

This scenarios don't work:

1. Server is down:
	Start the client
	Start the server
	--> Client do not a reconnect
	
2. Server is up:
	Start the client
	Client will connect to the server
	Kill the server
	Start the server
	--> Client do not a reconnect
