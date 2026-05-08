# AI-integrerad Spring Boot Service

Liten Spring Boot-applikation byggd för laboration 1 i kursen Web Services.

Ambitionsnivå: G.

Applikationen innehåller:
- personligheter via systemprompts
- minneshantering per sessionId
- integration mot OpenRouter
- några enklare tester

För att starta projektet behöver miljövariabeln OPENROUTER_API_KEY vara satt.

Projektet kan exempelvis testas via Insomnia mot:

http://localhost:8080/api/v1/chat

Skicka sedan en POST-request med en JSON-body i denna stil:

{
"personality": "gordon ramsay",
"message": "Can you review my Java code?",
"sessionId": "kitchen-nightmare-episode"
}

Nuvarande personligheter:
- coder
- gordon ramsay
- backwards

Den sista blev märkligare än planerat.

API-nyckeln är inte committad till repot!

...men jag skriver gärna ner den på en liten lapp för den som vill provköra projektet.

Efteråt kommer jag:
- rotera nyckeln
- skylla mig själv om mina tokens för 5 dollar försvinner spårlöst :-)
