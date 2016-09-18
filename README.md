# drunkr.io
Purdue CS 408 Software Testing Group Project

## Set up

1. Install lastest Eclipse (neon)
2. [Install Google Plugin for Eclipse](https://developers.google.com/eclipse/docs/install-eclipse-4.6)
3. [Install node.js and npm](https://docs.npmjs.com/getting-started/installing-node)
4. Run the following commands to get the web app dependencies set up or rebuilt after changes are made:
    ```
    cd Drunkr/war/
    npm install
    npm start
    ```
5. You can now run the server from within Eclipse using the standard Run > Run or Run > Debug menu commands.
6. Open [http://localhost:8888](http://localhost:8888)
7. **Optional:** If you would like to edit the front-end, [Visual Studio Code](https://code.visualstudio.com/) is the best option. To have the TypeScript files continually compiled to JavaScript, Open the `Drunkr/war` folder in VS Code and then press CMD+Shift+B (or Ctrl+Shift+B) to task the watch build task. This build task monitors the TypeScript files and compiles them on save.

**note:** every time you pull changes from the repo and there were modifications made to the front-end, you will need to run the commands above again to make sure everything is up to date.   
