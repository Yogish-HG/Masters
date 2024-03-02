import java.sql.SQLException;
import java.util.Map;
import java.util.Set;

public class PublicationLibrary {

    /**
     * Adds a publication to the database.
     *
     * @param identifier, the identifier of the publication
     * @param publicationInformation a map of the publication information
     * @return true if the publication was added successfully, false otherwise
     */
    public boolean addPublication(String identifier, Map<String, String> publicationInformation) {
        if (publicationInformation.containsKey("journal")) {
            return AddPublicationToDatabase.addJournal(identifier, publicationInformation);

        } else if (publicationInformation.containsKey("conference")) {
            return AddPublicationToDatabase.addConference(identifier, publicationInformation);
        }
        return false;
    }


    /**
     * Adds references to a publication in the database.
     *
     * @param identifier the identifier of the publication
     * @param references a set of reference identifiers
     * @return true if the references were added successfully, false otherwise
     */
    public boolean addReferences(String identifier, Set<String> references) {
        return AddReferencesToDatabase.AddReferences(identifier, references);
    }

    /**
     * Adds a venue to the database.
     *
     * @param venueName        the name of the venue
     * @param venueInformation a map of the venue information
     * @param researchAreas    a set of research areas associated with the venue
     * @return true if the venue was added successfully, false otherwise
     */
    public boolean addVenue( String venueName, Map<String, String> venueInformation, Set<String> researchAreas ) {
         return AddVenueToDatabase.populateVenueInformation(venueName, venueInformation, researchAreas);
    }

    /**
     * Adds a publisher to the database.
     * @param identifier the identifier of the publisher
     * @param publisherInformation a map of the publisher information
     * @return true if the publisher was added successfully, false otherwise
     */
    public boolean addPublisher( String identifier, Map<String, String> publisherInformation ) {
        return AddPublisherToDatabase.addPublisher(identifier, publisherInformation);
    }

    /**
     * Adds a research area to the database.
     *
     * @param researchArea the name of the research area
     * @param parentArea   a set of parent research areas
     * @return true if the research area was added successfully, false otherwise
     */
    public boolean addArea( String researchArea, Set<String> parentArea ) {
        return AddAreaToDatabase.populateResearchAreas(researchArea, parentArea);
    }

    /**
     * Gets the publication information for a given publication key.
     *
     * @param key the publication key to search for
     * @return a map of the publication information if found, or an empty map if not found
     */
    public Map<String, String> getPublications(String key) {
        return GetPublicationsFromDatabase.getPublications(key);
    }

    /**
     * Gets the citation count for a given author.
     *
     * @param author the name of the author to search for
     * @return the citation count for the author
     */
    public int authorCitations(String author){
        return GetAuthorCitations.authorCitations(author);
    }

    /**
     * Gets the research areas for a given author based on a threshold.
     *
     * @param author    the name of the author to search for
     * @param threshold the minimum number of papers for an area to be considered relevant
     * @return a set of relevant research areas for the author
     */
    public Set<String> authorResearchAreas(String author, int threshold){
        return GetAuthorResearchAreas.AuthorRAs(author, threshold);
    }

}
