# Shop Grid
Customizable shop grid for STS modding projects. Should work right out of the gate.

# Building
To build with debug features enabled:
* `mvn clean prepare-package package -P debug,common-settings`
* Building the debug mode will perform pre-packaging steps like mod ID refactoring.
* Debug only deposits the jar in the local STS mods folder

To build for production:
* `mvn clean prepare-package package -P prod,common-settings`
* No package preprocessing
* Prod build deposits the jar in the uploader folder and the local STS mods folder

# Uploading
`java -jar mod-uploader.jar upload -w MOD_NAME`
