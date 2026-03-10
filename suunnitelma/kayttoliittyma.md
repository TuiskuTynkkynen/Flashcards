# User Interface

## Flashcard View
![Flashcard View UI Diagram](/suunnitelma/card_view.png)

- Title of the selected deck (Label)  
- Current card  (VBox)
  - Card name or description depending on the active side (Labels)  
  - Flip card button  
- Current card index (Label)   
- Forward and backwards button   

## Deck View
![Deck View UI Diagram](/suunnitelma/deck_view.png)

- Current deck (VBox)
    - Deck name and description (Labels)  
    - Edit deck button  
- Previous and next deck buttons 
- Create and delete buttons  

## Edit View
![Edit View UI Diagram](/suunnitelma/edit_view.png)

- Deck information (Vbox)  
    - Deck name (Label)  
    - Deck description (Label)  
    - Card count (Label)  
- List of cards (VBox)  
    - Selected card with editable name/desctiption as TextAreas (VBox)  
    - Unselected cards with immutable name/desription as Labels (HBox)
- Card create and delete buttons  