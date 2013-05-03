#!/bin/bash
echo -ne "\n\n" && javac -d bin src/ru/ifmo/ctddev/evdokimov/task5/* && java -classpath ./bin ru.ifmo.ctddev.evdokimov.task5.Invoker ru.ifmo.ctddev.evdokimov.task5.Test print A
