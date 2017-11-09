import csv
import json

inputFilePath   = './consolidated-input-file.csv'
outputFilePath  = '1942-second-edition.json'

unitTypeList = []
unitTypeList.append('INFANTRY')
unitTypeList.append('ARTILLERY')
unitTypeList.append('TANK')
unitTypeList.append('FIGHTER')
unitTypeList.append('BOMBER')
unitTypeList.append('AA_GUN')
unitTypeList.append('DESTROYER')
unitTypeList.append('TRANSPORT')
unitTypeList.append('CRUISER')
unitTypeList.append('AIRCRAFT_CARRIER')
unitTypeList.append('SUBMARINE')
unitTypeList.append('BATTLESHIP')

territoryJsonList = []
with open(inputFilePath, 'r') as inputCsvFile:

    reader = csv.DictReader(inputCsvFile)
    for row in reader:

        neighbourList = [
            row['2'],
            row['3'],
            row['4'],
            row['5'],
            row['6'],
            row['7'],
            row['8'],
            row['9'],
            row['10'],
            row['11'],
        ]

        territoryJson = {}
        territoryJson['territoryName']  = row['TERRITORY']
        territoryJson['nationType']     = row['NATION']
        territoryJson['ipc']            = int(row['IPC'])
        territoryJson['territoryType']  = row['TERRITORY TYPE']
        territoryJson['neighbourNames'] = [val for val in neighbourList if val.strip() != '']
        territoryJson['units']          = []

        for unitType in unitTypeList:
            for i in range(0, int(row[unitType])):
                unitJson = {}
                unitJson['nationType']      = row['NATION']
                unitJson['unitType']        = unitType
                territoryJson['units'].append(unitJson)

        territoryJsonList.append(territoryJson)

with open(outputFilePath, 'w') as outputJsonFile:
    json.dump(territoryJsonList, outputJsonFile)