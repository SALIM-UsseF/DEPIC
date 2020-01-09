require 'digest'

module UtilsHelper

    def format_string_to_md5(string)
        Digest::MD5.hexdigest(string)
    end

end
