# ==== Etapa 1: build ====

FROM node:22-alpine AS build

WORKDIR /app

COPY package.json package-lock.json
RUN npm install

COPY . .
RUN npm run build

# ==== Etapa 2: runtime ====
FROM nginx:alpine

COPY --from=build /app/dist /usr/share/nginx/html

EXPOSE 80