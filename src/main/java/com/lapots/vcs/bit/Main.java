package com.lapots.vcs.bit;

public class Main {

    /*
            Refactor to support three commands
                bit init
                bit focus
                bit update (essentially the same as [init] though)

            Possible add sync with Google Drive aka bit-gdrive with
                additional command
                bit sync
     */
    public static void main(String[] args) {
        CommandUtils.processCommand(args);
    }

}
