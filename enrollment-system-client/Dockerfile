FROM node:16.17.1-alpine

WORKDIR /app/fe
COPY . /app/fe

RUN npm install -g @angular/cli@16.1.0
RUN npm install --legacy-peer-deps

CMD ["ng", "serve", "--host=0.0.0.0"]
