# AxisAllies
Java back end implementation of axis and allies.

- Project uses Maven!
- `mvn package`
- move into target directory
- run the jar using `java -jar <jar file name>`
- if maven execute plugin is installed run `mvn exec:java` or whichever goal is specified in the POM.

Following VS Code settings were used for this project:
```
{
    "editor.fontSize": 9,
    "window.zoomLevel": 1,
    "editor.minimap.enabled": false,
    "workbench.colorTheme": "Darcula Theme from IntelliJ",
    "java.jdt.ls.vmargs": "-noverify -Xmx1G -XX:+UseG1GC -XX:+UseStringDeduplication -javaagent:/Users/nik/.m2/repository/org/projectlombok/lombok/1.16.16/lombok-1.16.16.jar -Xbootclasspath/a:/Users/nik/.m2/repository/org/projectlombok/lombok/1.16.16/lombok-1.16.16.jar"
}
```
Note the setting to allow annotation processing through lombok.

Stack overflow why?:
```
        Territory A = territoryMap.get("A");
        Territory B = territoryMap.get("B");
        Territory C = territoryMap.get("C");

        List<Territory> BC = new ArrayList<>();
        BC.add(B);
        BC.add(C);

        List<Territory> AC = new ArrayList<>();
        AC.add(A);
        AC.add(C);

        List<Territory> AB = new ArrayList<>();
        AB.add(A);
        AB.add(B);

        A.setNeighbours(BC);
        B.setNeighbours(AC);
        C.setNeighbours(AB);
```

Double brace initialization?:
```
ArrayList<String> list = new ArrayList<String>() {{
    add("A");
    add("B");
    add("C");
}};
```
