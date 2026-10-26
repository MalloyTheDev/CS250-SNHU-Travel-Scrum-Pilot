# CS 250: Software Development Lifecycle

Portfolio artifacts and reflection from CS 250 at Southern New Hampshire University.

## About the project

ChadaTech asked our team to pilot Scrum on the SNHU Travel booking application before
deciding whether to move every team off the waterfall process. SNHU Travel wanted to grow
into niche vacation packages and gave us a working deadline of five weeks. Requirements
came out of a customer focus group, and the client changed direction twice inside those
five weeks, including a mid-project move of the whole booking tool toward detox and
wellness travel.

Across the project I worked one role at a time. As Product Owner I built and ranked the
Product Backlog, as Developer I built the Top Five Destinations list and the wellness
slide show, as Tester I turned acceptance criteria into pass and fail checks, and as
Scrum Master I ran the review and retrospective and made the recommendation to
leadership.

## Repository contents

Both documents are kept in their original Office format and as PDF. GitHub renders the PDF
copies in the browser, so they can be read without downloading anything.

- **Sprint Review and Retrospective**
  ([PDF](docs/pdf/Brendan_Malloy_CS250_Sprint_Review_and_Retrospective.pdf),
  [DOCX](docs/Brendan_Malloy_CS250_Sprint_Review_and_Retrospective.docx))
  The final project deliverable. It reviews how the Scrum roles were applied, how user
  stories were carried to Done, how the team absorbed two changes in direction, and it
  evaluates whether Scrum was the right choice for this project.
- **Agile and waterfall presentation**
  ([PDF](docs/pdf/Brendan_Malloy_CS250_Agile_Presentation.pdf),
  [PPTX](docs/Brendan_Malloy_CS250_Agile_Presentation.pptx))
  The presentation to ChadaTech leadership comparing Agile and waterfall, walking the
  wellness change through both approaches, and recommending an adoption path.
- **[Slide show source](slideshow/src/)**
  The Java Swing slide show built as Developer, covering the five wellness destinations.
  Source and image resources are tracked here; build output is not.

## The slide show

![The SNHU Travel slide show window, showing the first ranked wellness destination, a yoga
and mindfulness retreat, with its caption below the image and Previous and Next buttons
underneath](docs/images/slideshow.png)

The slide show is a single Java Swing class with no external dependencies. It was written
against Java 8 and compiles and runs on current JDKs. From the repository root:

```bash
cd slideshow/src
javac -d ../build SlideShow.java
jar --create --file ../SlideShow.jar --main-class SlideShow -C ../build . -C . resources
java -jar ../SlideShow.jar
```

Images are read from the classpath with `getResource`, so the packaged JAR runs without
the project folder beside it. Use **Previous** and **Next** to move through the five
destinations; navigation wraps in both directions.

## Reflection

### How do I interpret user needs and implement them into a program? How does creating user stories help with this?

User needs arrive as raw material, not as requirements. What came out of the SNHU Travel
focus group was a set of wants: travelers should be able to find vacations that suit
them, keep the preferences they set, and see deals worth their attention. My work as
Product Owner was to turn that into six backlog items, rank them by the value the client
cared about, size each one as small, medium, or large, and write the highest ones out as
full stories with acceptance criteria. The acceptance criteria are the part that converts
a want into something a program can be measured against, and writing them before any code
existed is what gave everyone the same definition of Done.

User stories help because of their shape. Naming the user, what they want, and why they
want it keeps the reason attached to the work instead of losing it somewhere between the
focus group and the editor. More than once the reason was the part I needed. The Save
Travel Preferences story said a returning traveler saves preferences so the site reflects
the trips they actually take, which told me those preferences had to survive past a single
visit, and I wrote that expectation into test case TC-01. The Top Five Destinations story
carried an acceptance criterion about the user clicking a link to reach the list. I had a
working list already, and measured against that criterion a bare list was not finished,
so I added a landing page with the link before I called the story Done. Left to my own
judgment I would have shipped it early.

The pilot also showed me where stories fail. Where mine were thin, the gaps showed up as
acceptance criteria nobody could test: the Personalized Travel Destination List said
unlikely destinations should be filtered out or ranked low without saying how, and nothing
set a limit on page load time or caption length. A criterion that cannot be measured
cannot be verified, so it quietly becomes an argument later. The rule I take forward is to
write acceptance criteria with limits that can actually be checked.

### How do I approach developing programs? What Agile processes do I hope to incorporate into my future development work?

I work from an ordered list toward a small, near finish line. I take the highest-value
item that is ready, build the smallest version that genuinely runs, and check it against
its own acceptance criteria before I call it done. Every Sprint repeats the full
lifecycle at reduced size: plan and analyze the chosen stories, design only what this
Sprint needs, write the code, test inside the Sprint rather than after it, deliver
something the client can try, then review and adapt. That rhythm is what let this project
absorb a change of direction instead of starting over. When SNHU Travel moved to wellness
travel, the cost was one updated slide show and one revised set of test cases.

Reading the code instead of assuming it works is the habit that paid off most. The slide
show change looked like pure content replacement, but while working in it I found that the
starter project added both the caption panel and the button panel to the same
`BorderLayout.SOUTH` region, and that region keeps only the last component added, so the
descriptions were never drawn at all. The new requirement called for text on every slide,
so the bug sat inside the Sprint Goal and I fixed it in the same Sprint by nesting both
panels in a footer panel. Developers are expected to adapt the Sprint Backlog as they
learn, as long as the work still serves the Sprint Goal.

The practices I want to carry into future work:

- **An ordered, sized backlog**, so what to work on next is a decision already made
  rather than a standing debate.
- **Acceptance criteria written before the code**, with limits that can be measured.
- **Test cases derived straight from acceptance criteria** and written in the same Sprint
  as the code, so a demo can show criteria being satisfied instead of just showing
  screens.
- **One shared board for decisions**, not email. Email was the real weak point of this
  pilot. Answers ended up scattered across separate inboxes, and the inspect and adapt
  cycle only works when everyone is looking at the same information.
- **Small increments that really run.** Exporting a runnable JAR meant the Tester
  exercised the same build I did rather than a description of it.
- **A retrospective that ends in one agreed action**, because a single change that
  actually happens beats a long list that does not.

### What does it mean to be a good team member in software development?

It starts with owning your own decisions and respecting the boundaries around everyone
else's. Our charter said nobody could walk work straight to a Developer outside the
planned Sprint Backlog, and that requests went through the Product Owner instead.
Honoring that rule when it is inconvenient is what keeps a Sprint Goal meaningful, and
protecting it is a large part of why the Scrum Master role exists.

Next is asking early, in writing, while answers are still cheap. My questions about the
test cases went to the Product Owner before any code existed, so when her answer arrived
as a wireframe with one destination per slide and Previous and Next buttons, the change
cost me a paper rewrite of three test cases and nothing more. How the question is asked
matters too. I learned to state my purpose in the first line, ask things that resolve to a
decision rather than a discussion, tie the request to a real event such as the next Daily
Scrum, and say where the answer would be recorded so the whole team would be working from
the same decision afterward.

Being a good team member also means pulling in the people downstream of you. When the
wellness change landed I wrote to the Product Owner and the Tester together rather than
only the person who could answer, which brought testing into the change at the start
instead of at the end. It means verifying your own work against the acceptance criteria
before handing it off, and testing past what the stories literally say: pressing Next on
the final slide was not written in any story, but a real user will do it, so I wrote a
case for it. One of my sharper questions to the Product Owner came out of test cases I had
written in a different role, which is a reminder that the useful questions usually come
from people who have actually sat in the next seat over.

Finally it means saying the honest thing in the retrospective. Scrum worked well here, but
it was not free: thin stories left real gaps, and any task waiting on the Product Owner's
answer simply waited. Reporting both sides is what made the recommendation to ChadaTech
worth acting on.

## Course information

- **Course:** CS 250: Software Development Lifecycle
- **Institution:** Southern New Hampshire University
- **Author:** Brendan Malloy
