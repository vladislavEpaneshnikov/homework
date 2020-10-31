How to Build:
backend: run "mvn clean install" from /backend
frontend: run "npm install" from /frontend

How to Run locally:
backend: run "mvn spring-boot:run" from /backend
frontend: run "npm start" from /frontend

How to deploy on docker: 

0) Just run build.bat

Manual way:

1) Build frontend with commands: 
cd frontend
npm install 
npm run build

2) Run "docker-compose up" from root directory

3) For redeployment with new changes run "docker-compose build --no-cache" and "docker-compose up"

(UI served on localhost:3000, Backend on localhost:8080)
