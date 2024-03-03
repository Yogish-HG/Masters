FROM node:17-alpine
WORKDIR /app
COPY ./package.json .
COPY ./package-lock.json .
RUN npm cache clean --force
RUN npm install
COPY . .
RUN npm run build
EXPOSE 3000
CMD ["npm", "start"]
