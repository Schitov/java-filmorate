# java-filmorate
Template repository for Filmorate project.

There are 6 tables are presented on the image below:
1. User- user data
2. Film - film data
3. Like - likes attached to movies by users
4. Friendship - friendship status between users
5. Genre - genre of films
6. Rating - rating of the Association of Film Companies (MPA)

![This is an image](https://github.com/Schitov/java-filmorate/blob/main/Filmorate_DB_%20Diagramma.png)

Queries to select main data:
1. TOP 10 films by count of likes
```sql 
SELECT FILM.FilmID, COUNT(Like.LikeID)
FROM Film
INNER JOIN Like on Film.FilmID = Like.FilmID
GROUP BY FilmID
ORDER BY COUNT(Like.LikeID) DESC
```

2. Common friends for 1-st and 2-nd users
```sql 
SELECT *
FROM User 
INNER JOIN Friendship on User.UserID = Friendship.UserID
WHERE UserID = 1
AND Friendship.FriendID IN 
(SELECT Friendship.FriendID
FROM User 
INNER JOIN Friendship on User.UserID = Friendship.UserID
WHERE UserID = 2)
```
