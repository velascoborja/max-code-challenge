# Digital Surgery Android Tech Test

We would like you to spend a few hours on the following technical task—create an Android application with two screens, some API requests, and a bit of database storing.

---

## Procedure List Page
This should be the home page of the app and it should show a list of procedure cards/cells, each with:

- A **thumbnail image**  
- The **procedure title**  
- The **phase count**  
- A **favourite button toggle**  

Clicking on one of the procedure items should show the user a **bottom sheet** dismissible view, containing all the detailed information of the procedure:

- **Card image** at the top  
- **Favourite button toggle**  
- **Title of the procedure**  
- **Total duration** in minutes  
- **Creation date** in `dd/mm/yyyy` format  
- A **horizontal list of phases**, each containing:
  - **Thumbnail image**  
  - **Phase name**  

---

## Favourite Procedures Page
Each time a user taps on the favourite button toggle, that procedure has to be stored in a **local database**. This favourite procedures list will display only the procedures marked as **favourites** (if any).

- Procedures must be **tappable** so that the user is able to see the **procedure details view**.  
- Each time a procedure's favourite state changes, the user should see a **Snackbar notification** showing the result.  

---

## White Label Challenge
In addition to the existing requirements, we would like you to **make the app configurable for multiple clients** using a **white-label approach**.

Each client should have **different branding and functionality**, while using the **same codebase**.

### Requirements:
- The app should support at least **two different clients**.
- Each client should have a **different visual style** (colors, logos, themes, etc.).
- The app should behave **differently per client**:
  - **Client A:** Procedures should be displayed in a **list**.
  - **Client B:** Procedures should be displayed in a **grid**.

Your solution should allow the app to scale and support additional clients in the future.

---

## Instructions
- This is your **fork of the project**. Feel free to push to the main branch.
- Upon completion, **push to main** and email us to let us know you have finished.
- **Do not include build folders or IDE-specific files.**

---

## Endpoints
- Procedures list: `https://staging.touchsurgery.com/api/v3/procedures`
- Procedure details: `https://staging.touchsurgery.com/api/v3/procedures/$PROCEDURE_ID`
  - Example: `https://staging.touchsurgery.com/api/v3/procedures/procedure-TSC_CemCup`

---

## Considerations
- **Production-ready code** → Write your code as if it were going to be published.
- Use the **latest stable Android Studio** version if possible.
- Provide a **brief explanation** of your solution, including **why** you made certain choices.

---

## Automated Tests
- Make sure your code is **testable** and include **unit tests**.
- **TDD is not mandatory**, but we would like to see **some representative tests**.
- UI tests are welcome, but not required.

---

## Technologies & Techniques to Use
You will be joining an existing team with existing technology. We want to see how well you work with these:

✅ **Dependency Injection** → Dagger/Hilt/Koin  
✅ **Database** → Room  
✅ **Networking** → Retrofit/KTor  
✅ **Async Programming** → Coroutines (or RxJava, if preferred)  
✅ **UI** → Jetpack Compose (or XML if you think it fits better)  
✅ **Architecture** → MVVM/MVI  
✅ **Testing** → Unit tests, UI testing with Espresso  

Our current app uses **Coroutines and Jetpack Compose**, but we still have some **RxJava and XML-based layouts**. Feel free to use what you think is best.

---

## Bonus Features (Optional but appreciated) 🎁
✅ **Search/filter functionality** for the procedures list.  
✅ **Offline mode**—ensure the app works when there’s no internet.  

---

## Final Notes
This challenge is meant to **evaluate your architecture, code quality, and ability to build scalable Android applications**. If you have any questions, feel free to reach out.

We’re excited to see your solution! 🚀
