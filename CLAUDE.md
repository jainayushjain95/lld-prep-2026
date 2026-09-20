# Project Instructions

## Creating a new module

When asked to create a new module (e.g. "create a model/module parallel to X, named Y"):

- Do NOT write any business logic or domain classes.
- Only wire up the module: the Maven `pom.xml` (parented off the root `lld-prep-2026` pom, matching the sibling modules' `exec-maven-plugin` config), the module's registration in the root `pom.xml` `<modules>` list, and a plain `Main.java` with an empty `main` method.
- Leave implementation of the actual LLD problem to a later, explicit request.
