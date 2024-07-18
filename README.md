# Distributed Systems: Accomodation Management System/Application

As part of the course "Distributed Systems" at the Department of Informatics of the Athens University of Economics and Business (AUEB), a simple accommodation management system was implemented, where users can perform functions as managers and tenants.

System Capabilities:

Manager Functionality:
Add Accommodations: Managers can add new accommodations through a console application.
Manage Dates: Managers can add available rental dates for their accommodations.
View Reservations: Managers can view the reservations for the accommodations they own.
View Total Reservations: Managers can view the total reservations per area for a specific period they specify.

Tenant Functionality:
Filter Accommodations: Tenants can filter accommodations based on the area, available dates, number of people, price, and stars.
Book Accommodations: Tenants can book the accommodations they are interested in.
Rate Rooms: Tenants can rate the rooms with stars (1-5).

System Architecture:

Backend:
Master and Worker Nodes: The system is implemented with one Master and multiple Workers, using the MapReduce model for parallel data processing.
Master Node: Implements a multithreaded TCP Server in Java, communicates with Workers, and manages reservations.
Workers: Also multithreaded and implemented in Java, store data in memory, and handle requests from the Master.

Frontend:
Android Application: Provides a UI for both tenant and manager functions.

Implementation Features:
TCP Connection: Communication is exclusively through TCP sockets.
MapReduce Framework: A programming model that allows for the parallel processing of large volumes of data.
Multithreading and Synchronization: The Master and Workers serve many users simultaneously, with synchronization through synchronized techniques, wait, and notify.
Android Application: Through which users can perform searches, bookings, etc.

This application offers a complete accommodation management system tailored to the needs of both managers and tenants, emphasizing interactivity and efficiency.

# Κατανεμημένα Συστήματα: Εφαρμογή/Σύστημα Διαχείρισης Καταλυμάτων

Στα πλαίσια του μαθήματος "Κατανεμημένα Συστήματα" του τμήματος Πληροφορικής του Οικονομικού Πανεπιστημίου Αθηνών (ΟΠΑ), υλοποιήθηκε ένα απλό σύστημα διαχείρισης καταλυμάτων, όπου οι χρήστες μπορούν να εκτελούν λειτουργίες ως διαχειριστές (managers) και ενοικιαστές.

Δυνατότητες Συστήματος:

Λειτουργία Διαχειριστή (Manager):
Προσθήκη Καταλυμάτων: Οι διαχειριστές μπορούν να προσθέτουν νέα καταλύματα μέσω ενός console application. 
Διαχείριση Ημερομηνιών: Οι διαχειριστές μπορούν να προσθέτουν διαθέσιμες ημερομηνίες προς ενοικίαση για τα καταλύματά τους.
Εμφάνιση Κρατήσεων: Οι διαχειριστές μπορούν να εμφανίζουν τις κρατήσεις για τα καταλύματα που έχουν στην ιδιοκτησία τους.
Εμφάνιση Συνολικών Κρατήσεων: Οι διαχειριστές μπορούν να εμφανίζουν τις συνολικές κρατήσεις ανα περιοχή για ένα συγκεκριμένο χρονικό διάστημα που θα δίνουν

Λειτουργία Ενοικιαστή (Tenant):
Φιλτράρισμα Καταλυμάτων: Οι ενοικιαστές μπορούν να φιλτράρουν τα καταλύματα με βάση την περιοχή, τις διαθέσιμες ημερομηνίες, το πλήθος των ατόμων, την τιμή και τα αστέρια.
Κράτηση Καταλυμάτων: Οι ενοικιαστές μπορούν να πραγματοποιούν κρατήσεις για τα καταλύματα που τους ενδιαφέρουν.
Βαθμολόγηση Δωματίων: Οι ενοικιαστές μπορούν να βαθμολογούν τα δωμάτια με αστέρια (1-5).

Αρχιτεκτονική Συστήματος:

Backend:
Master και Worker Nodes: Το σύστημα υλοποιείται με ένα Master και πολλούς Workers, χρησιμοποιώντας το μοντέλο MapReduce για την παράλληλη επεξεργασία δεδομένων.
Master Node: Υλοποιεί έναν πολυνηματικό TCP Server σε Java, επικοινωνεί με τους Workers, και διαχειρίζεται τις κρατήσεις.
Workers: Οι Workers, επίσης πολυνηματικοί και υλοποιημένοι σε Java, αποθηκεύουν δεδομένα στη μνήμη και διαχειρίζονται αιτήματα από τον Master.

Frontend:
Android Application: Παρέχει UI για τις λειτουργίες ενοικιαστή και διαχειριστή

Χαρακτηριστικά Υλοποίησης:
TCP Σύνδεση: Η επικοινωνία υλοποιείται αποκλειστικά μέσω TCP sockets
Map Reduce framework: Προγραμματιστικό μοντέλο που επιτρέπει την παράλληλη επεξεργασία μεγάλων όγκων δεδομένων.
Πολυνηματικότητα και Συγχρονισμός: Ο Master και οι Workers εξυπηρετούν πολλούς χρήστες ταυτόχρονα, με συγχρονισμό μέσω τεχνικών synchronized, wait και notify.
Android εφαρμογή: Μέσω της οποίας οι χρήστες μπορούν να πραγματοποιήσουν αναζήτηση, κρατήσεις, κ.λ.π.

Η εφαρμογή αυτή προσφέρει ένα ολοκληρωμένο σύστημα διαχείρισης καταλυμάτων, προσαρμοσμένο στις ανάγκες τόσο των διαχειριστών όσο και των ενοικιαστών, με έμφαση στη διαδραστικότητα και την αποτελεσματικότητα.
