import csv

details = []
graph = []

with open('/Users/nik/Documents/development/AxisAlliesMaven/src/main/resources/1942-territory-details.csv', 'rb') as csvfile:
    reader = csv.reader(csvfile)
    for row in reader:
        rowDict = {}
        key = row[0].strip()
        valueList = [ val.strip() for val in row[1:]]
        valueDict = {}

        valueDict['ipc'] = valueList[0]
        valueDict['nation'] = valueList[1]
        valueDict['newName'] = valueList[-1]
        valueDict['INFANTRY'] = valueList[2]
        valueDict['ARTILLERY'] = valueList[3]
        valueDict['TANK'] = valueList[4]
        valueDict['FIGHTER'] = valueList[5]
        valueDict['BOMBER'] = valueList[6]
        valueDict['AA_GUN'] = valueList[7]
        valueDict['DESTROYER'] = valueList[8]
        valueDict['TRANSPORT'] = valueList[9]
        valueDict['CRUISER'] = valueList[10]
        valueDict['AIRCRAFT_CARRIER'] = valueList[11]
        valueDict['SUBMARINE'] = valueList[12]
        valueDict['BATTLESHIP'] = valueList[13]
        
        if(len(key) < 3):
            valueDict['newName'] = "Sea Zone " + valueList[-1]
        else:
            valueDict['newName'] = valueList[1]
        rowDict[key] = valueDict
        print rowDict
        details.append(rowDict)
