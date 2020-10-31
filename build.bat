cd frontend
call npm install
call npm run build
cd ..
call docker-compose build --no-cache
call docker-compose up