#!/usr/bin/env bash

curl -u JuanKrzemien:Juankrzemien2017 http://52.45.166.109:8080/rest/raven/1.0/export/test?keys=GTPFX-1 -o src/test/resources/features/GTPFX-1.feature

#unzip src/test/resources/features/features.zip -d src/test/resources/features/

# rm src/test/resources/features/features.zip

echo "Running tests..."

mvn clean test -Djava.net.useSystemProxies=true

curl -H "Content-Type: application/json" -X POST -u JuanKrzemien:Juankrzemien2017 –data @target/live-ui-cucumber.json http://52.45.166.109:8080/rest/raven/1.0/import/execution/cucumber






