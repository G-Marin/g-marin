#include <stdio.h>
#include <ctype.h>

enum Statetype {NORMAL, COMMENTSTART, COMMENT, NESTEDCOM, COMMENTCLOSE, STRINGLOPEN, CHARLOPEN, ESCAPEDSTRING, ESCAPEDCHAR};

/* Implements the NORMAL state of the DFA. c is the current DFA character, linepointer keeps track of current line, errorLinepointer keeps track of line number of unterminated comments. Updates current line and possible error line. Writes c to stdout, as specified by the DFA. Detects whether a possible comment, character literal, or string literal has begun. Returns next state */

enum Statetype
handleNormal(int c, int *linepointer, int *errorLinepointer){

    enum Statetype state;

    if (c == '\n'){
        (*linepointer)++;
        putchar(c);
        state = NORMAL; 
    }
    else if (c == '/'){
        state = COMMENTSTART;
    }
    else if(c == '\''){
        putchar(c);
        state = CHARLOPEN;
    } 
    else if(c == '"'){
        putchar(c);
        state = STRINGLOPEN;
    } 
    else {
    putchar(c);
    state = NORMAL;
    }
    return state;
}

/* Implement the COMMENTSTART state of the DFA. c is a the current DFA character, linepointer keeps track of current line, errorLinepointer keeps track of line number of unterminated comments. Updates current line and possible error line. Writes c to stdout, as specified by the DFA. Detects whether a character literal, string literal, or comment has started. Returns next state*/

enum Statetype
handleCommentStart(int c, int *linepointer, int *errorLinepointer){

    enum Statetype state;

    if (c == '\n'){
        (*linepointer)++;
        printf("/");
        putchar(c);
        state = NORMAL;
    } 
    else if (c == '\''){
        printf("/");
        putchar(c);
        state = CHARLOPEN;
    } 
    else if(c == '"'){
        printf("/");
        putchar(c);
        state = STRINGLOPEN;
    } 
    else if(c == '*'){
        printf(" ");
        *errorLinepointer = *linepointer;
        state = COMMENT;
    } 
    else if(c == '/'){
        putchar(c);
        state = COMMENTSTART;
    }
    else {
        printf("/");
        putchar(c);
        state = NORMAL;
    }
    return state;
}


/* Implement the COMMENT state of the DFA. c is the current DFA character, linepointer keeps track of current line, errorLinepointer keeps track of line number of unterminated comments. Updates current line and possible error line. Writes c to stdout, as specified by the DFA. Detects when a nestedcomment has begun or a comment has started to close. Returns next state.*/

enum Statetype
handleComment(int c, int *linepointer, int *errorLinepointer){

    enum Statetype state;

    if (c == '\n'){
        (*linepointer)++;
        putchar(c);
        state = COMMENT;
    } 
    else if (c == '*'){  
        state = COMMENTCLOSE;
    } 
    else if (c == '/'){
        state = NESTEDCOM;
    } 
    else {
        state = COMMENT;
    }
    return state;
}


/* Implement the NESTEDCOM state of the DFA. c is the current DFA character, linepointer keeps track of current line, errorLinepointer keeps track of line number of unterminated comments. Updates current line and possible error line. Writes c to stdout, as specified by the DFA. Returns next state*/

enum Statetype
handleNestedCom(int c, int *linepointer, int *errorLinepointer){
   
    enum Statetype state;
    if(c == '\n'){
        (*linepointer)++;
        putchar(c);
    }
    state = COMMENT;
    return state;
}

/* Implement COMMENTCLOSE state of the DFA. c is the current DFA character, linepointer keeps track of current line, errorLinepointer keeps track of line number of unterminated comments. Updates current line and possible error line. Writes c to stdout as specified by DFA. Detects if comment has ended and prints a space as specified by DFA */

enum Statetype
handleCommentClose(int c, int *linepointer, int *errorLinepointer){

    enum Statetype state;

    if (c == '\n'){
        (*linepointer)++;
        putchar(c);
        state = COMMENT;
    } 
    else if(c == '/'){
        state = NORMAL;
    } 
    else if(c == '*'){
        state = COMMENTCLOSE;
    }
    else {
        state = COMMENT;
    }
    return state;
}

/* Implement STRINGLOPEN state of the DFA. c is the current DFA character, linepointer keeps track of current line, errorLinepointer keeps track of line number of unterminated comments. Updates current line and possible error line. Writes c to stdout as specified by DFA. Detects when string literal has closed and escaped characters. Returns next state*/

enum Statetype
handleStringLOpen(int c, int *linepointer, int *errorLinepointer){
    
    enum Statetype state;

    if (c == '\n'){
        (*linepointer)++;
        putchar(c);
        state = STRINGLOPEN;
    } 
    else if(c == '"'){
        putchar(c);
        state = NORMAL;
    } 
    else if (c == '\\'){
        putchar(c);
        state = ESCAPEDSTRING;
    } 
    else{
        putchar(c);
        state = STRINGLOPEN;
    }
    return state;
}

/* Implement CHARLOPEN state of the DFA. c is the current DFA character, linepointer keeps track of current line, errorLinepointer keeps track of line number of unterminated comments. Updates current line and possible error line. Writes c to stdout as specified by DFA. Detects when character literal has closed and escaped characters. Returns next state*/

enum Statetype
handleCharLOpen(int c, int *linepointer, int *errorLinepointer){

    enum Statetype state;
    
    if (c == '\n'){
        (*linepointer)++;
        putchar(c);
        state = CHARLOPEN;
    } 
    else if(c == '\''){
        putchar(c);
        state = NORMAL;
    } 
    else if(c == '\\'){
        putchar(c); 
        state = ESCAPEDCHAR;
    } 
    else {
        putchar(c);
        state = CHARLOPEN;
    }
    return state;
}

/* Implement ESCAPEDSTRING state of the DFA. c is the current DFA character, linepointer keeps track of current line, errorLinepointer keeps track of line number of unterminated comments. Updates current line and possible error line. Writes c to stdout as specified by DFA. Returns next state*/

enum Statetype
handleEscapedString(int c, int *linepointer, int *errorLinepointer){

    enum Statetype state;
    
    if (c == '\n'){
        (*linepointer)++;
        putchar(c);
        state = STRINGLOPEN;
    } 
    else {
    state = STRINGLOPEN;
    putchar(c);
}
    return state;
}


/* Implement ESCAPEDCHAR state of the DFA. c is the current DFA character, linepointer keeps track of current line, errorLinepointer keeps track of line number of unterminated comments. Updates current line and possible error line. Writes c to stdout as specified by DFA. Returns next state*/

enum Statetype
handleEscapedChar(int c, int *linepointer, int *errorLinepointer){

    enum Statetype state;
    
     if (c == '\n'){
        (*linepointer)++;
        putchar(c);  
        state = CHARLOPEN;
    } 
    else{
    state = CHARLOPEN;
    putchar(c);
    }
    return state;
}


/* Read text from stdin. Replaces comments with spaces or returns line of unterminated comments. Keeps track of line number as text is read and line number of possible unterminated comments. Write the result to stdout. Returns 0 unless unterminated comment exists*/

int main(void){

    enum Statetype state = NORMAL;    
    int c, currentLine, errorLine;
    currentLine = 1;
    errorLine = 0;


    while ((c = getchar()) != EOF){
        switch(state){
            case NORMAL:
                state = handleNormal(c, &currentLine, &errorLine);
                break;
            case COMMENTSTART:
                state = handleCommentStart(c, &currentLine, &errorLine);
                break;
            case COMMENT:
                state = handleComment(c, &currentLine, &errorLine);
                break;
            case NESTEDCOM:
                state = handleNestedCom(c, &currentLine, &errorLine);
                break;
            case COMMENTCLOSE:
                state = handleCommentClose(c, &currentLine, &errorLine);
                break;
            case STRINGLOPEN:
                state = handleStringLOpen(c, &currentLine, &errorLine);
                break;
            case CHARLOPEN:
                state = handleCharLOpen(c, &currentLine, &errorLine);
                break;
            case ESCAPEDSTRING:
                state = handleEscapedString(c, &currentLine, &errorLine);
                break;
            case ESCAPEDCHAR:
                state = handleEscapedChar(c, &currentLine, &errorLine);
                break;
        }
    }


    if(state == COMMENTSTART){
        printf("/");
    }

    if(state == COMMENT || state == NESTEDCOM || state == COMMENTCLOSE){
        fprintf(stderr, "Error: line %d: unterminated comment\n" ,errorLine);
        return 1;
    } 
    
    return 0;
}
