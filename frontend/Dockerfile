FROM node:14.20.1-bullseye

WORKDIR /app
COPY package*.json ./

RUN npm install

COPY . .
EXPOSE 9002
CMD ["npm","run","serve"]