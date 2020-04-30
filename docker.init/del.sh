#!/bin/bash
NAME=test_db
docker stop ${NAME}
docker rm ${NAME}
docker rmi ${USER}/${NAME}
