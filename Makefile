all: run
# for linux, just make to run project.
run:
	./mvnw spring-boot:run

test:
	./mvnw test