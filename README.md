![ShowcaseFX](report/showcasefx.png)

## Overview

A Showcase/ Tour-guide component for JavaFx application inspired by Material Design guidelines.


## Quick Start

```java
Showcase showcase = new Showcase(root);
		
showcase.createStep(button, "This is a nice button.");
showcase.createStep(label, "A small and cute label.");
showcase.createStep(textField, "Oh, look at this awesome text field !");

showcase.start();
```

## Architecture

### Steps, Target and content

Each Steps associates a target node and a content node. **Target** nodes are components to highlight and **content** nodes are extra information added to the scene. 
Each step can embed custom layout and layering strategies to override the Showcase defaults.

### Layer and Layouts

The Showcase component rendering is based on two main objects: **Layer** and **Layout**. The layer defines how a target node will be highlighted (the background and the associated shape). The layout manages the content placement strategy over the layer.


## Basic usage

### Manage Layers

Built-In Layers

- ShowcaseLayerShape: This type of layers allows you to define custom JavaFx shapes that will be  subtracted to a background shape sized as the showcase. This will result in a transparent gap, highlighting a part of the scene.

- ShowcaseLayerFill: Showcase fills allows you to submit background fills as layer. This si usefull to render gradients or background effects.

If built-in layers doesn't fit your needs you can always define new ones by extending the `ShowcaseLayer`, `ShowcaseLayerShape` or `ShowcaseLayerFill` classes.


### Manage Layouts
NOTE: When you are working with built-in showcase layouts you must fix the size of content nodes as the resizing behaviour are not handled by layouts. Use medhods .`setPrefSize()` or  `setMaxSize()` on content nodes or add css rules `-fx-pref-width`, `-fx-pref-height`, `-fx-max-width`, `-fx-max-height`.
Built-in Layout:
- AutoShowcaseLayout (Auto. placement strategy)
- Tooltip ShowcaseLayout (Put content node inside tooltips)
- RelativeShowcaseLayout (Place the content node with given `x.y` offsets from target)
- AlignmentShowcaseLayout (Place the content node aligned with showcase bounds: `TOP`, `BOTTOM_LEFT`,etc...)

Example
```
showcase.setLayout(new AutoShowcaseLayout());
```

If built-in layouts doesn't fit your needs you can always define a new one by extending the `ShowcaseLayout` class.


### Events and Behaviours

Showcase behaviour can be customized using **Events** handlers and **Behaviours** to quickly cover common use cases.

`showcase.setOnClickBehaviour(ShowcaseBehaviour.NEXT);`

Events handler properties: `setOnShowcaseStarted`, `setOnShowcaseStopped`, `setOnShowcaseStepDisplay`



### Style


| Component | Css classes                                            |
|-----------|--------------------------------------------------------|
| Showcase  | `showcase`                                             |
| Step      | `showcase-step-content`, `simple-step-view`            |
| Layer     | `showcase-layer`, `showcase-layer-shape`               |
| Layout    | `showcase-tooltip-content`, `showcase-tooltip-pointer` |

