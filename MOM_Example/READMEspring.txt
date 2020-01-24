**enable virtual machine on windows (boot menu)
**install docker desktop (install package)
**install rabbitmq erlang (shell)

check docker is functioning
> docker ps

run docker
>docker run -d --hostname my-rabbit --name some-rabbit -p 8080:15672 -p 5672:5672 rabbitmq:3-management

check docker container properties
>docker ps

check rabbitMQ management
>open any browser
>localhost:8080
>credentials guest/guest

Keine responds von web Monitor durch Probleme beim Routing -> Alternate exchange https://www.rabbitmq.com/ae.html 
