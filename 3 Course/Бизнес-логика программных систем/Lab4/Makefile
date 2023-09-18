.PHONY: compose-app-up
compose-app-up:
	docker build -f Business-logic-main-Consumer/demo/Dockerfile -t consumer .
	docker build -f Business-logic-main-Producer/demo/Dockerfile -t producer .
	docker build -f blps/Dockerfile -t camunda .
	docker-compose build
	docker-compose up -d postgres
	docker-compose up -d consumer
	docker-compose up -d producer
	docker-compose up -d camunda

.PHONY: compose-app-rm
compose-app-rm:
	docker-compose down