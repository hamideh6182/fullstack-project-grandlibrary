# fullstack-project-grandlibrary
This is a Repository of the capstone of the Java Development Bootcamp by neue fische.
This is a library management app that allows admins to manage books and users to search for and borrow books.
# Features
. Admins can add new books, adjust quantities, or remove books from the library.<br/>
. Users can search for books by title or category.<br/>
. Users can borrow books.<br/>
. Users can return books.<br/>
<h1>Getting Started</h1>
<h3>Prerequisites</h3>
. JDK 19 or higher<br/>
. Maven<br/>
. Node.js<br/>
. MongoDB<br/>
. Docker<br/>
<h3>Environment Variables</h3>
Before running the app, make sure to set the following environment variables:

. MONGO_URI: the connection string for MongoDB, in the format mongodb://[SERVER]:[PORT]<br/>
. CLOUDINARY_URL: the Cloudinary URL, in the format cloudinary://[API_KEY]:[API_SECRET]@[CLOUD_NAME]

<h3> Installation </h3>
. To install the app, clone the repository. Start backend with Maven and start the frontend with npm start.<br/>
. To grant an admin user access, manually update the role field to "ADMIN" in the users collection in the MongoDB instance.

<h3>Usage</h3>
. As an admin, log in to the app using your credentials. From there, you can add, adjust, or remove books as needed.<br/>
. As a user, use the search bar to look for books by title or category. Once you find a book you want to borrow, click the "Checkout" button to add it to your borrowed books. When you are done with a book, click the "Return" button to return it to the library.
