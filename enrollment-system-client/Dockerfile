FROM node:16.17.1-alpine

WORKDIR /app/fe
COPY . /app/fe

RUN npm install --legacy-peer-deps

CMD ["ng", "install"]
