<h1>MédiLabo Solutions</h1>
<hr  />
<em>Open Classrooms Project 9 - "Développez une solution en microservices pour votre client"</em>

This project is a Spring Boot & Angular fullstack application with a mySQL and mongoDB databases.  The application is dedicated to help medical practitioner finding warnings about their patient's probability to develop diabetes wich is calculated from patient's notes based on keywords.<br />
MediLabo Solution is splited into 4 backend microservices and one for the frontend.

<hr />
<h2>How to launch the app</h2>
First of all, you need to dowload the projet in any way to have it on your computer and make sure you have <a href="https://www.docker.com/">docker</a> installed on it. The next step is to build the 5 docker images, on per microservice. Here's the process :<br />
- open your terminal and navigate into the ms-patient folder<br />
- run the following command on your terminal : `docker build -t ms-patient .`<br />
- navigate into the ms-notes folder<br />
- run the following command on your terminal : `docker build -t ms-notes .`<br />
- do the same for each microservice using those specifics image names :<br />
ms-diabetes-report : `docker build -t ms-diabetes-report .`<br />
ms-api-gateway : `docker build -t ms-gateway .`<br />
frontend : `docker build -t angular-frontend .`<br /><br />

Next, you need to build the docker container. Go back to the main folder (named MediLabo_Solutions) in your terminal. Run the following command : `docker-compose up -d`<br /><br />

Now, you should be able to access the application at http://localhost:4200/ . Now, you can login with the following accounts :<br /><br />

<em>To see, add and update patients infos</em><br />
username : Organizer<br />
password : demo<br /><br />

<em>To see patients infos, notes and diabetes report and add new note for a patient</em><br />
username : Practitioner<br />
password : demo

<hr />
<h2>Green Code improvments recommandations (content in french)</h2>
- mettre en mémoire les résultats des appels des microservices<br />
- réduire les logs en production <br />
- compresser les réponses JSON <br />
- réduire les champs DTO pour supprimer ceux qui ne sont pas utilisés
