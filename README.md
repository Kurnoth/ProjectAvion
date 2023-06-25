# ProjectAvion

Ce projet a été entièrement développé en Java et utilise une base de données MySQL

## Manuel d'utilisation
1. Si nécessaire, installer MySQL et la librairie Connector/J : https://dev.mysql.com/downloads/connector/j/ 
2. Compilez les fichiers dans le dossier src avec : javac – cp  "Route jusqu’à"/mysql-connector-j.jar -d classes src/*.java 
3. Lancez l’application server : java -cp "Route jusqu’à"/mysql-connector-java.jar:classes ProjectAvion.src.Server 
4. Lancez l’application Client : java -cp classes ProjectAvion.src.Client 
5. Dans l’application client donner les coordonnées de votre radar <Latitude> <Longitude> 

Vous pouvez maintenant observer les avions, cliquez sur un eux pour envoyer un ordre de changement
