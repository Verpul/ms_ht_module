FROM node:19.9.0-alpine

WORKDIR /app

EXPOSE 8081

COPY package*.json ./

RUN npm install

COPY . .

CMD ["npm", "run", "serve"]
