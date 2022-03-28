FROM mcr.microsoft.com/mssql/server:2019-latest
WORKDIR /user/src/app
ENV ACCEPT_EULA Y
ENV SA_PASSWORD 14256958Sa
COPY . /user/src/app
CMD /bin/bash ./run-initialization.sh & ./entrypoint.sh